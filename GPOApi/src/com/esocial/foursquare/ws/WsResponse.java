package com.esocial.foursquare.ws;



public class WsResponse {

	private int statusCode;
	private String responseString;
	private Error error;
	
	public WsResponse() {
	}
	
	public WsResponse(int statusCode, String responseString) {
		super();
		this.statusCode = statusCode;
		this.responseString = responseString;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getResponseString() {
		return responseString;
	}
	
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	public Error getError() {
		return error;
	}

	public boolean failed() {
		return error != null;
	}

}
