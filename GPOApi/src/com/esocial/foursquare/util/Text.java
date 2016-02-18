package com.esocial.foursquare.util;


public class Text {

	
	public static Boolean isEmpty(String text){
		if (text == null || "".equals(text)) return true;
		return false;
	}
	
	public static String leftZeroComp(String text, int digits){
		String output = text;
		while (output.length() < digits) output = "0" + output;
		return output;
	}
	
	public static String leftCharacterComp(String text, String character,  int digits){
		String output = text;
		while (output.length() < digits) output = character + output;
		return output;
	}
	
	public static String rightCharacterComp(String text, String character,  int digits){
		if (text == null) text = "";
		String output = text;
		while (output.length() < digits) output = output + character;
		return output;
	}

}
