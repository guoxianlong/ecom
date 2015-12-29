package com.alimama.ad.ssp.task.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class IndexController {
	
	@RequestMapping(value = "/index")
    @ResponseBody
	public String index(HttpServletRequest request){
		return "{\"code\":200}";
	}
}
