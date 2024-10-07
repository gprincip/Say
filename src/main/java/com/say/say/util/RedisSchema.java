package com.say.say.util;

import com.say.say.model.RedisKeys;

/**
 * 
 * @author Gavrilo
 * Class containing static methods for obtaining Redis keys for objects
 */
public class RedisSchema {

	public static String createUserSayingsCacheKey(String username) {
		return RedisKeys.USERSAYINGS.getKey() + ":" + username;
	}

	public static String createUserSayingsCacheKey(Long id) {
		return RedisKeys.SAYING.getKey() + ":" + id;
	}
	
	/**
	 * Timestamp of when user made his last post
	 * @return
	 */
	public static String createUserLastPostTimestampKey(Long userId) {
		return RedisKeys.USER_LAST_POST_TIMESTAMP.getKey() + ":" + userId.toString();
	}
	
}
