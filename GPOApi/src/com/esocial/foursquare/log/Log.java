package com.esocial.foursquare.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
	
	public static void i (int code, String message, String data) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String msg = "- Info " + String.valueOf(code) + " - " + timeStamp + ": " + message + " -- " + data; 
		System.out.println(msg);
	}
	
	public static void w (int code, String message, String data) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String msg = "- Warning " + String.valueOf(code) + " - " + timeStamp + ": " + message; 
		System.out.println(msg);
	}

	public static void e (int code, String message, String data) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String msg = "- Error " + String.valueOf(code) + " - " + timeStamp + ": " + message; 
		System.out.println(msg);
	}
	
}
