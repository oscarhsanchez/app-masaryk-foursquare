package com.esocial.foursquare.ws.response;

import java.util.ArrayList;

import com.esocial.foursquare.obj.Ubicacion;
import com.esocial.foursquare.ws.WsResponse;

public class GetLocationsResponse extends WsResponse {
	
	public String result;
	public ArrayList<Ubicacion> ubicaciones;
		
}
