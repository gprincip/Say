package com.say.say.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.say.say.dao.SayingDaoDb;
import com.say.say.dao.SayingDaoRedis;
import com.say.say.model.Saying;
import com.say.say.redis.RedisConnectionProvider;

import redis.clients.jedis.Jedis;

@Service
public class RedisService{

	private static final Logger log = LoggerFactory.getLogger(RedisService.class);
	
	@Autowired
	SayingDaoDb sayingsDao;
	
	@Autowired
	SayingDaoRedis sayingRedisDao;
	
	@Autowired
	RedisConnectionProvider redis;
	
	public void testRedis() {
		
		Jedis jedis = new Jedis("localhost", 6379);
        // Test the connection by setting and getting a value
        jedis.set("key", "Hello Redis!");
        String value = jedis.get("key");

        System.out.println("Value from Redis: " + value);

        // Close the connection
        jedis.close();
		
	}
	

	public void addSayingsForUserToRedisCache(Long userId) {
		
		List<Saying> sayings = sayingsDao.getSayingsFromUserId(userId);
		sayingRedisDao.addUserSayingsToCache(userId, sayings);
	}
	
	@Async
	public void addSayingsForUserToRedisCacheAsync(Long userId) {	
		addSayingsForUserToRedisCache(userId);
	}
	
}
