package com.ecom.dto;

public class ResultDTO {
	private int code;
	private String message = "";
	private Object data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public static ResultDTO createFailResult(int code, String message){
		ResultDTO r = new ResultDTO();
		r.setCode(code);
		r.setMessage(message);
		return r;
	}
	
	public static ResultDTO createFailResult(String message){
		ResultDTO r = new ResultDTO();
		r.setCode(200);
		r.setMessage(message);
		return r;
	}

	public static ResultDTO createFailResult(int code, String message, Object data){
		ResultDTO r = new ResultDTO();
		r.setCode(code);
		r.setMessage(message);
		r.setData(data);
		return r;
	}
	
	public static ResultDTO createOKResult(Object data){
		ResultDTO r = new ResultDTO();
		r.setCode(200);
		r.setData(data);
		return r;
	}
}
