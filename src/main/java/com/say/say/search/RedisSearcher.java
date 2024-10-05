package com.say.say.search;

import org.springframework.beans.factory.annotation.Autowired;

import com.say.say.model.SayingsSearchParameters;
import com.say.say.service.RedisService;

public class RedisSearcher implements ISearcher {

	@Autowired
	RedisService redisService;
	
	@Override
	public SayingsSearchResult searchSayingsByText(SayingsSearchParameters searchData) {
		
		
		
		return null;
	}

}
