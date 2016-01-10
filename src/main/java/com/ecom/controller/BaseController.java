package com.ecom.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;

public class BaseController {
	public final String APP_KEY = "EE385CC63E0983D9F2EE4FF79750D633";
	public final String APP_SECRET = "f2a3bb06a3e64b899f8f4717614d07e4";
	public final String JD_HOST = "https://api.jd.com/routerjson";
	public final String AUTH_HOST = "http://www.test.com:8080/api/auth";
	
	public String getAccessToken(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
		    if(cookie.getName().equals("access_token")){
		    	return cookie.getValue();
		    }
		}
		return null;
	}
	
	public JdClient getJdClient(HttpServletRequest request){
		JdClient client = new DefaultJdClient(JD_HOST,getAccessToken(request),APP_KEY,APP_SECRET);
		return client;
	}
	 
}
