package com.say.say.dao;

import java.time.LocalDateTime;

import com.say.say.model.Saying;

public interface SayingDaoRedis {
	
	public void save(Saying saying);

	public void saveUserLastPostTimestamp();

	public LocalDateTime getUserLastPostTimestamp(Long userId);

}
