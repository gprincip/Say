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
	
	public List<Saying> searchSayingsContainingAnyGivenTags(List<Long> tagIds){
		
		return sayingRepo.getSayingsContainingAnyGivenTags(tagIds);
		
	}
	
	public List<Integer> searchSayingsContainingExactlyGivenTags(List<Long> tagIds){
		
		String tagsCsv = "";
		for(Long id : tagIds) {
			tagsCsv += id + ",";
		}
		
		tagsCsv = tagsCsv.substring(0, tagsCsv.length()-1); //remove last coma
		
		return sayingRepo.getSayingsContainingExactlyGivenTags(tagsCsv, Integer.valueOf(tagIds.size()).toString());
		
	}
	
}
