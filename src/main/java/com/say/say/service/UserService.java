package com.say.say.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.say.say.config.ApplicationProperties;
import com.say.say.dao.SayingDaoRedisImpl;

@Component
public class UserService {
	
	@Autowired
	SayingDaoRedisImpl sayingRedisDao;
	
	@Autowired
	ApplicationProperties config;
	
	/**
	 * Checks how much time user have to wait until he can post again
	 * @param userId
	 * @return how much user must wait until he can post again </br> 
	 * 		   0 means client is allowed to post</br>
	 * 	       anything else represents waiting time in miliseconds
	 */
	public String timeUntilPostingCooldownExpired(Long userId) {
		
		LocalDateTime lastPostTimestamp = sayingRedisDao.getUserLastPostTimestamp(userId);
		if(lastPostTimestamp != null) {
				
			LocalDateTime now = LocalDateTime.now();
			Long postingCooldownMillis = Long.parseLong(config.getPostingCooldown());
			
			if(lastPostTimestamp.isBefore(now) && postingCooldownMillis != null && 
					(Duration.between(lastPostTimestamp, now)).toMillis() > postingCooldownMillis) {
				return "0";
			}else {
				 Long waitingTime = (postingCooldownMillis - (Duration.between(lastPostTimestamp, now)).toMillis());
				 return waitingTime.toString();
			}
		}else {
			return "0";
		}

	}
	
}
