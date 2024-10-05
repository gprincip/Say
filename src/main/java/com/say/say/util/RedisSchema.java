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
	
}
