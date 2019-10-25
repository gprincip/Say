package com.say.say.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.say.say.dao.repository.SayingRepository;
import com.say.say.model.Saying;

@Component
public class SayingSearcher {

	@Autowired
	SayingRepository sayingRepo;
	
	public List<Saying> searchByTags(List<Long> tagIds){
		
		return sayingRepo.getSayingsWithTags(tagIds);
		
	}
	
}
