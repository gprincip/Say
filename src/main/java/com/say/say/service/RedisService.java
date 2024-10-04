package com.say.say.service;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisService {

	public void testRedis() {
		
		Jedis jedis = new Jedis("localhost", 6379);
        // Test the connection by setting and getting a value
        jedis.set("key", "Hello Redis!");
        String value = jedis.get("key");

        System.out.println("Value from Redis: " + value);

        // Close the connection
        jedis.close();
		
	}
	
}
