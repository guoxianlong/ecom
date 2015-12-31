package com.ecom.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecom.common.HttpClientUtils;
import com.ecom.dto.JDUser;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.category.CategorySearchRequest;
import com.jd.open.api.sdk.response.category.CategorySearchResponse;

@Controller
@RequestMapping(value = "/api")
public class AuthController {
	private String APP_KEY = "EE385CC63E0983D9F2EE4FF79750D633";
	private String APP_SECRET = "f2a3bb06a3e64b899f8f4717614d07e4";
	private String AUTH_HOST = "http://www.test.com:8080/api/auth";
	
	@RequestMapping(value = "/auth")
	@ResponseBody
	public String auth(HttpServletRequest request) throws Exception {
		String code = request.getParameter("code");
		JDUser user = getAccessToken(code);
		
		JdClient client=new DefaultJdClient("https://api.jd.com/routerjson",user.getAccessToken(),APP_KEY,APP_SECRET); 
		CategorySearchRequest r=new CategorySearchRequest();
		r.setFields( "id,fid,status,lev,name,index_id" );
		CategorySearchResponse response=client.execute(r);
		
		return JSON.toJSONString(response);
	}

	private JDUser getAccessToken(String accessCode) throws Exception {
		String url = "https://oauth.jd.com/oauth/token?grant_type=authorization_code&client_id=" + APP_KEY
				+ "&client_secret=" + APP_SECRET + "&redirect_uri=" + URLEncoder.encode(AUTH_HOST, "utf-8") + "&code=" + accessCode
				+ "&state=1234";
		
		String jsonStr = HttpClientUtils.post(url, 3000);
		JSONObject json = JSON.parseObject(jsonStr);
		
		JDUser user = new JDUser();
		user.setAccessToken(json.getString("access_token"));
		user.setRefreshToken(json.getString("refresh_token"));
		user.setUid(json.getString("uid"));
		user.setUserNick(json.getString("user_nick"));
		return user;
	}
}
