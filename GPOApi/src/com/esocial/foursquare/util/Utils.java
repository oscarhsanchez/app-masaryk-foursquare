package com.esocial.foursquare.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Utils {

	public static String getTokenFromString(String pk_value) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");			
			return Base64.encode(Utils.byteArrayToHexString(md.digest(("psub/!*20aa" + pk_value).getBytes("UTF-8"))));

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
					Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

	public static String md5(String input) {  

		String md5 = null;  

		if(null == input) return null;  

		try {  
			//Create MessageDigest object for MD5  
			MessageDigest digest = MessageDigest.getInstance("MD5");  
			//Update input string in message digest  
			digest.update(input.getBytes(), 0, input.length());  
			//Converts message digest value in base 16 (hex)  
			md5 = new BigInteger(1, digest.digest()).toString(16);  
		} catch (NoSuchAlgorithmException e) {  

			e.printStackTrace();  
		}  
		return md5;  
	}  
}
