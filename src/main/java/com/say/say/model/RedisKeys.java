package com.say.say.model;

public enum RedisKeys {

	USERSAYINGS("cache:usersayings"), SAYING("cache:saying"), USER_LAST_POST_TIMESTAMP("cache:userlastpost");

	RedisKeys(String key) {
		this.key = key;
	}
	
	String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
