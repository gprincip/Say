package com.say.say.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.say.say.dao.SayingDaoDbImpl;
import com.say.say.dao.repository.SayingRepository;
import com.say.say.model.Saying;

@Component
public class SayingSearcher {

	@Autowired
	SayingDaoDbImpl sayingDao;
	
	public List<Saying> searchSayingsContainingAnyGivenTags(List<Long> tagIds){
		
		return sayingDao.getSayingsContainingAnyGivenTags(tagIds);
		
	}
	
	public List<Long> searchSayingsContainingExactlyGivenTags(List<Long> tagIds){
		
		List<Long> result = sayingDao.getSayingsContainingExactlyGivenTags(tagIds, "2");
		
		return result;
		
	}
	
}
