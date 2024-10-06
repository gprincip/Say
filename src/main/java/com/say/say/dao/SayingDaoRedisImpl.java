package com.say.say.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.say.say.model.Saying;
import com.say.say.service.RedisService;
import com.say.say.util.RedisSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Repository
public class SayingDaoRedisImpl implements SayingDao{

	@Autowired
	RedisService redisService;
	
	@Override
	public void save(Saying saying) {
		
		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
		
			try (Jedis jedis = jedisPool.getResource()) {
				jedis.hmset(RedisSchema.createUserSayingsCacheKey(saying.getId()), saying.toMap());
			}
		
		jedisPool.close();
		
	}

	@Override
	public List<Saying> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
