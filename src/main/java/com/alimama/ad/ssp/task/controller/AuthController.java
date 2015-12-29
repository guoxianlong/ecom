package com.alimama.ad.ssp.task.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class AuthController {
	private String APP_KEY = "";
	private String APP_SECRET = "";
	
	@RequestMapping(value = "/auth")
	@ResponseBody
	public String auth(HttpServletRequest request) {
		String code = request.getParameter("code");
		String accessToken = getAccessToken(code);
		return accessToken;
	}

	private String getAccessToken(String accessCode) {
		String url = "https://oauth.jd.com/oauth/token?grant_type=authorization_code&client_id=" + APP_KEY
				+ "&client_secret=" + APP_SECRET + "&scope=read&redirect_uri=http://" + url + "&code=" + accessCode
				+ "&state=1234";
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setRequestProperty("Accept-Charset", "utf-8");
		conn.setRequestMethod("POST");
		int code = conn.getResponseCode();
		InputStream is = conn.getInputStream();
		String jsonStr = inputStream2String(is);
		
	}
}
