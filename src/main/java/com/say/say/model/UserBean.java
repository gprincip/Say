package com.say.say.model;

public class UserBean {

	private String userIp;

	public UserBean() {}
	public UserBean(String userIp) {
		this.userIp = userIp;
	}
	
	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	@Override
	public String toString() {
		return "User [userIp=" + userIp + "]";
	}
	
}
