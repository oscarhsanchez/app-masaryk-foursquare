package com.esocial.foursquare.ws.request;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.esocial.foursquare.ws.WsRequest;

public class LoginRequest extends WsRequest {

	private final String metodo = "login";        

	public <T> T execute(String username, String password, String countryid,  Class<T> responseClass) {	

		List<NameValuePair> param = new ArrayList<NameValuePair>(2);
		param.add(new BasicNameValuePair("username", username));		
		param.add(new BasicNameValuePair("password", password));
		param.add(new BasicNameValuePair("countryid", countryid));

		InetAddress ip;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();
			sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		param.add(new BasicNameValuePair("deviceid", sb.toString()));


		return super.executePost(metodo, param, responseClass);
	}


}
