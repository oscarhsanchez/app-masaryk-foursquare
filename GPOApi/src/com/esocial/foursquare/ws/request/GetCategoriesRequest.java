package com.esocial.foursquare.ws.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.esocial.foursquare.ws.WsRequest;

public class GetCategoriesRequest extends WsRequest {
	
	private final String metodo = "metadata/ubicaciones/foursquare/categories/";        

	public <T> T execute(Class<T> responseClass) {	
				
		List<NameValuePair> param = new ArrayList<NameValuePair>(2);		
		
		return super.executeGetDefaultHeaders(metodo, param, responseClass);
	}
	
	
}
