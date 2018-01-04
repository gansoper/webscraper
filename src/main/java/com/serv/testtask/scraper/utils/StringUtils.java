package com.serv.testtask.scraper.utils;

public class StringUtils {
	
	public static final String PREFIX = "/url?q=";
	
	public static final String POSTFIX = "&";
	
	public static String getNormalLink(String googlelink){
		if (googlelink.contains(PREFIX));
			googlelink = googlelink.substring(PREFIX.length());
		if (googlelink.contains(POSTFIX));
			googlelink = googlelink.substring(0, googlelink.indexOf(POSTFIX));
			
		return googlelink;
			
	}
	
}
