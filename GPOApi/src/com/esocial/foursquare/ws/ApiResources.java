package com.esocial.foursquare.ws;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import com.esocial.foursquare.log.Log;
import com.esocial.foursquare.obj.FsCategory;
import com.esocial.foursquare.obj.FsVenue;
import com.esocial.foursquare.obj.Resources;
import com.esocial.foursquare.ws.request.GetCategoriesRequest;
import com.esocial.foursquare.ws.request.GetLocationsRequest;
import com.esocial.foursquare.ws.request.LoginRequest;
import com.esocial.foursquare.ws.request.RenewTokenRequest;
import com.esocial.foursquare.ws.request.SendCategoriesRequest;
import com.esocial.foursquare.ws.request.SendVenuesRequest;
import com.esocial.foursquare.ws.response.GetCategoriesResponse;
import com.esocial.foursquare.ws.response.GetLocationsResponse;
import com.esocial.foursquare.ws.response.LoginResponse;
import com.esocial.foursquare.ws.response.RenewTokenResponse;
import com.esocial.foursquare.ws.response.SendCategoriesResponse;
import com.esocial.foursquare.ws.response.SendVenuesResponse;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class ApiResources {

	public LoginResponse login(String username, String password, String countryid) {		
		try {
			LoginResponse resp = new LoginRequest().execute(username, password, countryid, LoginResponse.class);
			if (resp != null && resp.result.equals("OK")){
				Resources.session = resp.Session;
				return resp;
			}
			else
				return null;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(400, "Error On Login", e.getMessage() + " - " + sw.toString());
			return null;
		}
	}

	public boolean renewToken(String session_token, String renew_token, String countryid) {		
		try {
			RenewTokenResponse resp = new RenewTokenRequest().execute(session_token, renew_token, countryid, RenewTokenResponse.class);
			if (resp != null && resp.result.equals("OK")){
				Resources.session = resp.Session;
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(400, "Error On Renew", e.getMessage() + " - " + sw.toString());
			return false;
		}
	}

	public GetLocationsResponse getLocations() {

		try {
			GetLocationsResponse resp = (new GetLocationsRequest()).execute(GetLocationsResponse.class);			
			if (resp != null && resp.result.equals("OK"))
				return resp;
			else
				return null;

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(400, "Error On Request getLocations", e.getMessage() + " - " + sw.toString());
			return null;
		}
	}

	public ArrayList<FsVenue> searchVenues(String pk_ubicacion, String fk_pais, Double latitude, Double longitude){
		if(latitude != 0d || longitude != 0d){
			String ll = latitude + "," + longitude;
			ArrayList<FsVenue> venues = new ArrayList<FsVenue>();
			FoursquareApi foursquareApi = new FoursquareApi(Resources.CLIENT_ID, Resources.CLIENT_SECRET, "app://venues");

			// After client has been initialized we can make queries.
			Result<VenuesSearchResult> result;
			try {
				result = foursquareApi.venuesSearch(ll, null, null, null, null, 50, null, null,
						null, null, null, Resources.radius, null);
			} catch (FoursquareApiException e) {
				Log.e(400, "ERROR FOURSQUARE", e.getMessage());
				return null;
			}

			if (result!=null && result.getMeta().getCode() == 200) {
				Log.i(200, "GET FOURSQUARE VENUES", "COUNT: "+result.getResult().getVenues().length);
				for(CompactVenue cVenue : result.getResult().getVenues()){
					FsVenue venue = new FsVenue(pk_ubicacion, fk_pais, cVenue);
					venues.add(venue);
				}

				return venues;

			} else {
				// Proper error handling
				Log.e(result.getMeta().getCode(), result.getMeta().getErrorType(), result.getMeta().getErrorDetail());
				return new ArrayList<FsVenue>();
			}
		}else{
			Log.i(200, "INVALID LAT-LNG", "pk_ubicacion: "+ pk_ubicacion +" Latitude: 0.0 Longitude: 0.0");
			return new ArrayList<FsVenue>();
		}
	}

	public GetCategoriesResponse getCategories() {

		try {
			GetCategoriesResponse resp = (new GetCategoriesRequest()).execute(GetCategoriesResponse.class);			
			if (resp != null && resp.result.equals("OK"))
				return resp;
			else
				return null;

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(400, "Error On Request getCategories", e.getMessage() + " - " + sw.toString());
			return null;
		}
	}

	public ArrayList<FsCategory> searchCategories(){	
		ArrayList<FsCategory> categories = new ArrayList<FsCategory>();
		FoursquareApi foursquareApi = new FoursquareApi(Resources.CLIENT_ID, Resources.CLIENT_SECRET, "app://categories");
		// After client has been initialized we can make queries.
		Result<Category[]> result = null;
		try {
			result = foursquareApi.venuesCategories();
		} catch (FoursquareApiException e) {
			Log.e(400, "ERROR FOURSQUARE", e.getMessage());
			return null;
		}

		if (result!=null && result.getMeta().getCode() == 200) {
			for(Category fsCat : result.getResult()){
				Resources.categoriesHierarchy.put(fsCat.getId(), "");
				saveCategories(fsCat);
				
				FsCategory cat = new FsCategory(fsCat);
				categories.add(cat);
			}

			return categories;

		} else {
			// Proper error handling
			Log.e(result.getMeta().getCode(), result.getMeta().getErrorType(), result.getMeta().getErrorDetail());
			return null;
		}
	}

	private void saveCategories(Category parent){
		if(parent.getCategories()!=null){
			for(Category child : parent.getCategories()){
				Resources.categoriesHierarchy.put(child.getId(), parent.getId());
				saveCategories(child);
			}
		}
	}

	public Boolean sendVenues(ArrayList<FsVenue> venues) {
		try {
			SendVenuesResponse resp = (new SendVenuesRequest()).execute(venues, SendVenuesResponse.class);
			if (resp != null && resp.result.equals("OK"))
				return true;
			else
				return false; 
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(400, "Error On Request sendVenues", e.getMessage() + " - " + sw.toString());
			return false;
		}
	}

	public Boolean sendCategories(ArrayList<FsCategory> categories) {
		try {
			SendCategoriesResponse resp = (new SendCategoriesRequest()).execute(categories, SendCategoriesResponse.class);
			if (resp != null && resp.result.equals("OK"))
				return true;
			else
				return false; 
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e(400, "Error On Request sendCategories", e.getMessage() + " - " + sw.toString());
			return false;
		}
	}

}
