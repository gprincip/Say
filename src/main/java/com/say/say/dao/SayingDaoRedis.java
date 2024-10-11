package com.say.say.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.say.say.model.Saying;

public interface SayingDaoRedis {
	
	public void save(Saying saying);

	public void saveUserLastPostTimestamp();

	public LocalDateTime getUserLastPostTimestamp(Long userId);
	
	public void addUserSayingsToCache(Long userId, List<Saying> sayings);

	public void addSayingToUserSayingsCache(Saying saying, Long id);
	
	public List<Saying> getSayingsFromUserId(Long userId);

}
