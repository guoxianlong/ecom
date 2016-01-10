package com.ecom.controller;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecom.common.HttpClientUtils;
import com.ecom.dto.JDUser;

@Controller
@RequestMapping(value = "/api")
public class AuthController extends BaseController{
	
	@RequestMapping(value = "/auth")
	@ResponseBody
	public String auth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		JDUser user = getAccessToken(code);
		
		Cookie cookie = new Cookie("access_token", user.getAccessToken());
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return JSON.toJSONString(user);
	}

	private JDUser getAccessToken(String accessCode) throws Exception {
		String url = "https://oauth.jd.com/oauth/token?grant_type=authorization_code"
				+ "&client_id=" + APP_KEY
				+ "&client_secret=" + APP_SECRET 
				+ "&redirect_uri=" + URLEncoder.encode(AUTH_HOST, "utf-8") 
				+ "&code=" + accessCode
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
