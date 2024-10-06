package com.say.say.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.say.say.config.ApplicationProperties;
import com.say.say.dao.SayingDaoDbImpl;
import com.say.say.dao.repository.SayingRepository;
import com.say.say.model.Saying;

@Component
public class UserService {

	@Autowired
	SayingDaoDbImpl sayingDao;
	
	@Autowired
	ApplicationProperties config;
	
	/**
	 * Checks how much time user have to wait until he can post again
	 * @param clientIp
	 * @return how much user must wait until he can post again </br> 
	 * 		   0 means client is allowed to post</br>
	 * 	       anything else represents waiting time in miliseconds
	 */
	public String timeUntilPostingCooldownExpired(String clientIp) {
		
		Saying lastSaying = sayingDao.getLastSayingFromIp(clientIp);
		if(lastSaying != null) {
				
			Date date = lastSaying.getDate();
			Date now = new Date();
			Long postingCooldownMillis = Long.parseLong(config.getPostingCooldown());
			
			if(date.before(now) && postingCooldownMillis != null && 
					(now.getTime() - date.getTime()) > postingCooldownMillis) {
				return "0";
			}else {
				 Long waitingTime = (postingCooldownMillis - (now.getTime() - date.getTime()));
				 return waitingTime.toString();
			}
		}else {
			return "0";
		}

	}
	
}
