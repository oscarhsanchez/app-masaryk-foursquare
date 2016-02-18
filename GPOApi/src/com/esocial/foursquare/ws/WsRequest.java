package com.esocial.foursquare.ws;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.esocial.foursquare.log.Log;
import com.esocial.foursquare.obj.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class WsRequest {

	public static final int REQUEST_TYPE_POST = 1;
	public static final int REQUEST_TYPE_GET = 2;
	public static final int REQUEST_TYPE_PUT = 3;

	/**
	 * Constructs a WsResponse object and calls the webservice to know the string response that will be
	 * deserialized later.
	 * @param accept
	 * @param contentType
	 * @param requestType
	 * @param jsonAction
	 * @param parameters
	 * @param addSessionToken
	 * @param addSessionCookie
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public WsResponse execute(String accept, String contentType, Integer requestType, String jsonAction, List<NameValuePair> parameters, Boolean addSessionToken, Boolean addSessionCookie, List<NameValuePair> headers) throws Exception {

		Log.i(200, "WsRequest", jsonAction);

		HttpClient httpClient = new DefaultHttpClient();
		UrlEncodedFormEntity entity = null;
		if (parameters != null) entity = new UrlEncodedFormEntity(parameters);
		HttpResponse httpResp = null;

		switch(requestType){
			case REQUEST_TYPE_POST:
				HttpPost post = new HttpPost(Resources.apiUrl + jsonAction);
				post.addHeader("Content-type", contentType);
				if (accept != null && !accept.equals("")) post.setHeader("Accept", accept);
				if (headers != null) {
					for (NameValuePair pair : headers) {
						post.addHeader(pair.getName(), pair.getValue());
					}
				}
				post.setEntity(entity);
				httpResp = httpClient.execute(post);
				break;
			case REQUEST_TYPE_GET:

				String param = "";
				for (int i=0; i<parameters.size(); i++){
					NameValuePair pair = parameters.get(i);
					param += (param == null || param.equals("")) ? "" : "&";
					param += pair.getName() + "=" + URLEncoder.encode(pair.getValue());
				}

				if (param != null && !param.equals("")) param = "?" + param;

				HttpGet get = new HttpGet(Resources.apiUrl + jsonAction + param);
				get.addHeader("Content-type", contentType);
				get.addHeader("Accept", accept);
				if (headers != null) {
					for (NameValuePair pair : headers) {
						get.addHeader(pair.getName(), pair.getValue());
					}
				}
				httpResp = httpClient.execute(get);
				break;
			case REQUEST_TYPE_PUT:
				HttpPut put = new HttpPut(Resources.apiUrl + jsonAction);
				put.addHeader("Content-type", contentType);
				put.addHeader("Accept", accept);
				if (headers != null) {
					for (NameValuePair pair : headers) {
						put.addHeader(pair.getName(), pair.getValue());
					}
				}
				put.setEntity(entity);
				httpResp = httpClient.execute(put);
				break;
		}

		String strResponse = EntityUtils.toString(httpResp.getEntity());
		
		Log.i(200, "WsRequest response", strResponse);
		//Log.i(200, "WsRequest", "--------------------------------------------------------------------------------");
		//Log.i(200, "WsRequest", "--------------------------------------------------------------------------------");

		int statusCode = httpResp.getStatusLine().getStatusCode();

		WsResponse wsResponse = new WsResponse(statusCode, strResponse);

		Log.i(200, "WsRequest", jsonAction + " - Terminado");

		return wsResponse;

	}

	/**
	 * Executes a webservice action, base method for all the others
	 * @param accion
	 * @param params
	 * @param clase
	 * @param method
	 * @param headers
	 * @param gson
	 * @return
	 */
	public <T> T execute(String accion, List<NameValuePair> params, Class<T> clase, int method, List<NameValuePair> headers, Gson gson) {
		T response = null;
		try {
			WsResponse wsResponse = execute("application/json", "application/x-www-form-urlencoded", 
					method, accion, params, true, false, headers);
			if (wsResponse != null) {
				response = gson.fromJson(wsResponse.getResponseString(), clase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Executes a WebService action with default deserializer and custom headers
	 * @param accion
	 * @param params
	 * @param clase
	 * @param method
	 * @param headers
	 * @return
	 */
	public <T> T execute(String accion, List<NameValuePair> params, Class<T> clase, int method, List<NameValuePair> headers) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return execute(accion, params, clase, method, headers, gson);
	}
	
	public <T> T executeGet(String accion, List<NameValuePair> params, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_GET, null);
	}
	
	public <T> T executePost(String accion, List<NameValuePair> params, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_POST, null);
	}
	
	public <T> T executePut(String accion, List<NameValuePair> params, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_PUT, null);
	}
	
	public <T> T executeGet(String accion, List<NameValuePair> params, List<NameValuePair> headerParams, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_GET, headerParams);
	}
	
	public <T> T executePost(String accion, List<NameValuePair> params, List<NameValuePair> headerParams, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_POST, headerParams);
	}
	
	public <T> T executePut(String accion, List<NameValuePair> params, List<NameValuePair> headerParams, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_PUT, headerParams);
	}
	
	public <T> T executeGetDefaultHeaders(String accion, List<NameValuePair> params, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_GET, getDefaultHeaders());
	}
	
	public <T> T executePostDefaultHeaders(String accion, List<NameValuePair> params, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_POST, getDefaultHeaders());
	}
	
	public <T> T executePutDefaultHeaders(String accion, List<NameValuePair> params, Class<T> clase) {
		return execute(accion, params, clase, REQUEST_TYPE_PUT, getDefaultHeaders());
	}
	
	private List<NameValuePair> getDefaultHeaders() {
		List<NameValuePair> header = new ArrayList<NameValuePair>();
		header.add(new BasicNameValuePair("Authorization", Resources.session.access_token));
		return header;		
		
	}
	
	/**
	 * Executes a GET with default headers, and custom deserializer (used for Timeline deserializing)
	 * @param accion
	 * @param params
	 * @param clase
	 * @param deserializer
	 * @return
	 */
	public <T> T executeGetDefaultHeadersCustomDeserializer(String accion, List<NameValuePair> params, Class<T> clase, Gson deserializer) {
		return execute(accion, params, clase, REQUEST_TYPE_GET, getDefaultHeaders(), deserializer);
	}
	
}



