package com.esocial.foursquare.obj;

import java.util.ArrayList;
import java.util.HashMap;

import com.esocial.foursquare.ws.ApiResources;


public class Resources {
	
	public static String apiUrl = "http://api.gpovallas.com/";
	public static String lastUpdate = "1700-01-01";
	public static Integer state = 1;
	public static Double longitude;
	public static Double latitude;
	public static ApiResources resources = new ApiResources();
	public static Session session;
	
	//Foursquare data
	public static String CLIENT_ID = "PKIMTRDRMG0PMZ43LG4CUD4YDAUYHBBL3DR0USX1WMOXPAX4";
	public static String CLIENT_SECRET = "RNKAGFQJCCROL4H0L3JVQJ1YR1GVJ1Q1I1IMGY2ALAITEMQO";
	public static Integer radius = 1000;
	public static ArrayList<FsVenue> venues;
	public static ArrayList<FsCategory> categories;
	
	public static HashMap<String, String> categoriesHierarchy = new HashMap<String, String>();
	
	
}
