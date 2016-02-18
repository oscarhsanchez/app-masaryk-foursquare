package com.esocial.foursquare.ws.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.esocial.foursquare.ws.WsRequest;

public class GetLocationsRequest extends WsRequest {
	
	private final String metodo = "ubicaciones";        

	public <T> T execute(Class<T> responseClass) {	
				
		List<NameValuePair> param = new ArrayList<NameValuePair>(2);
		param.add(new BasicNameValuePair("estatus", "INSTALADO"));			
		
		return super.executeGetDefaultHeaders(metodo, param, responseClass);
	}
	
	
}
