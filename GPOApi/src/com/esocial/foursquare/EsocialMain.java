package com.esocial.foursquare;

import java.util.ArrayList;

import com.esocial.foursquare.log.Log;
import com.esocial.foursquare.obj.FsVenue;
import com.esocial.foursquare.obj.Resources;
import com.esocial.foursquare.obj.Ubicacion;
import com.esocial.foursquare.ws.response.GetLocationsResponse;
import com.esocial.foursquare.ws.response.LoginResponse;

public class EsocialMain {

	public static void main(String[] args) {

		//Login
		LoginResponse login = Resources.resources.login("admin", "123", "MX");
		if(login==null){
			System.out.println("Error on login");
			return;
		}

		//Peticion a foursquare para recoger las categorias
		Resources.categories = Resources.resources.searchCategories();

		//Enviamos las categorias
		Boolean sendCatResp = Resources.resources.sendCategories(Resources.categories);
		if(sendCatResp){
			Log.i(200, "SEND CATEGORIES OK", "COUNT: " + Resources.categories.size());
		}else{
			System.out.println("KO");
		}

		//Peticion a la api para recoger las ubicaciones
		GetLocationsResponse locResp = Resources.resources.getLocations();
		if(locResp!=null){
			//Peticion a foursquare para recoger venues
			Resources.venues = new ArrayList<FsVenue>();
			for(Ubicacion ub : locResp.ubicaciones){
				Resources.venues.addAll(Resources.resources.searchVenues(ub.pk_ubicacion, ub.fk_pais,
						ub.latitud, ub.longitud));
			}
		}

		//Enviamos los venues
		Boolean sendVenResp = Resources.resources.sendVenues(Resources.venues);
		if(sendVenResp){
			Log.i(200, "SEND VENUES OK", "COUNT: " + Resources.venues.size());
		}



	}

}
