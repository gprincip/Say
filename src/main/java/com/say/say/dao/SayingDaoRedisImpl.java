package com.say.say.dao;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.say.say.model.LoggedUser;
import com.say.say.model.Saying;
import com.say.say.redis.RedisConnectionProvider;
import com.say.say.service.RedisService;
import com.say.say.util.JsonUtil;
import com.say.say.util.RedisSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Repository
public class SayingDaoRedisImpl implements SayingDaoRedis{

	private static final Logger log = LoggerFactory.getLogger(SayingDaoRedisImpl.class);
	
	@Autowired
	RedisConnectionProvider redis;
	
	@Autowired
	LoggedUser user;
	
	public void save(Saying saying) {
				
			try (Jedis jedis = redis.getJedisPool().getResource()) {
				jedis.hmset(RedisSchema.createSayingsCacheKey(saying.getId()), saying.toMap());
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
	
	public void addUserSayingsToCache(Long userId, List<Saying> sayings) {

		AtomicInteger ai = new AtomicInteger(0);
		Collection<List<Saying>> partitioned = sayings.stream().
				collect(Collectors.groupingBy(user -> ai.getAndIncrement() / 100)).values();
				

		try (Jedis jedis = redis.getJedisPool().getResource()) {

			int partitionNo = 1;
			long start = 0;
			for (List<Saying> partition : partitioned) {

				log.info("Adding user's (" + userId + ") sayings to the redis cache. Partition: " + partitionNo + "/" + partitioned.size());
				start = System.currentTimeMillis();

				Pipeline pipeline = new Pipeline(jedis);

				for (Saying saying : partition) {

					saying.addTagsToList();
					String sayingJson = JsonUtil.sayingToJson(saying);
					pipeline.sadd(RedisSchema.createUserSayingsCacheKey(userId), sayingJson);

				}

				pipeline.sync();
				pipeline.close();
				log.info("Finished adding user's (" + userId + ") sayings to the redis cache in " + (System.currentTimeMillis() - start) + " ms");

			}
		}
	}

	@Override
	public void addSayingToUserSayingsCache(Saying saying, Long userId) {
		
		try (Jedis jedis = redis.getJedisPool().getResource()) {
			

			String sayingJson = JsonUtil.sayingToJson(saying);
			jedis.sadd(RedisSchema.createUserSayingsCacheKey(userId), sayingJson);
			log.info("Added new saying from user: (" + userId + ") to its cache!");
			
		}
		
	}
}
