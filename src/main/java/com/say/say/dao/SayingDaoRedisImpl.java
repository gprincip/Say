package com.say.say.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.say.say.model.LoggedUser;
import com.say.say.model.Saying;
import com.say.say.redis.RedisConnectionProvider;
import com.say.say.service.RedisService;
import com.say.say.util.RedisSchema;

import redis.clients.jedis.Jedis;

@Repository
public class SayingDaoRedisImpl implements SayingDao{

	@Autowired
	RedisService redisService;
	
	@Autowired
	RedisConnectionProvider redis;
	
	@Autowired
	LoggedUser user;
	
	@Override
	public void save(Saying saying) {
				
			try (Jedis jedis = redis.getJedisPool().getResource()) {
				jedis.hmset(RedisSchema.createUserSayingsCacheKey(saying.getId()), saying.toMap());
			}
		
	}
	
	public void saveUserLastPostTimestamp() {
		try(Jedis jedis = redis.getJedisPool().getResource()){
			jedis.set(RedisSchema.createUserLastPostTimestampKey(user.getUser().getId()), LocalDateTime.now().toString());
		}	
	}

	public LocalDateTime getUserLastPostTimestamp(Long userId) {
		
		try(Jedis jedis = redis.getJedisPool().getResource()){
			String timestamp = jedis.get(RedisSchema.createUserLastPostTimestampKey(userId));
			if(StringUtils.isNotBlank(timestamp)) {
				LocalDateTime localDateTime = LocalDateTime.parse(timestamp);
				return localDateTime;
			}
		}	
		return null;
		
	}
	
	@Override
	public List<Saying> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
