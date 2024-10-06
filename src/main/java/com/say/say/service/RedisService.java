package com.say.say.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.say.say.dao.SayingDaoDbImpl;
import com.say.say.dao.repository.SayingRepository;
import com.say.say.model.RedisKeys;
import com.say.say.model.Saying;
import com.say.say.util.JsonUtil;
import com.say.say.util.RedisSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService{

	private static final Logger log = LoggerFactory.getLogger(RedisService.class);
	
	@Autowired
	SayingDaoDbImpl sayingsDao;
	
	public void testRedis() {
		
		Jedis jedis = new Jedis("localhost", 6379);
        // Test the connection by setting and getting a value
        jedis.set("key", "Hello Redis!");
        String value = jedis.get("key");

        System.out.println("Value from Redis: " + value);

        // Close the connection
        jedis.close();
		
	}
	

	public void addSayingsForUserToRedisCache(String username) {
		
		List<Saying> sayings = sayingsDao.getSayingsFromUsername(username);
		
		AtomicInteger ai = new AtomicInteger(0);
		Collection<List<Saying>> partitioned = sayings.stream().
				collect(Collectors.groupingBy(user -> ai.getAndIncrement() / 100)).values();
		
		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
		
		try (Jedis jedis = jedisPool.getResource()) {
		
			int partitionNo = 1;
			long start = 0;
			for(List<Saying> partition : partitioned) {
				
				log.info("Adding user's (" + username + ") sayings to the redis cache. Partition: " + partitionNo + "/" + partitioned.size());
				start = System.currentTimeMillis();
				
				for(Saying s : partition) {
					
					String sayingJson = JsonUtil.sayingToJson(s);
					jedis.sadd(RedisSchema.createUserSayingsCacheKey(username), sayingJson);
					
				}
				
			}
			
				
			log.info("Finished adding user's (" + username + ") sayings to the redis cache in " + (System.currentTimeMillis() - start) + " ms");
		}
		
		jedisPool.close();
	}
	
	@Async
	public void addSayingsForUserToRedisCacheAsync(String username) {	
		addSayingsForUserToRedisCache(username);
	}
	
}
