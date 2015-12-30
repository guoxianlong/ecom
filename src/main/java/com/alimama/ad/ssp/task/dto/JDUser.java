package com.alimama.ad.ssp.task.dto;

public class JDUser {
	private String AccessToken;
	private String refreshToken;
	private int ExpiresIn;
	private long time;
	private String uid;
	private String userNick;
	public String getAccessToken() {
		return AccessToken;
	}
	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public int getExpiresIn() {
		return ExpiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		ExpiresIn = expiresIn;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
}
