package com.say.say.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.say.say.dao.repository.SayingRepository;
import com.say.say.model.Saying;

@Repository
public class SayingDaoDbImpl implements SayingDao{

	@Autowired
	SayingRepository sayingRepo;
	
	@Override
	public void save(Saying saying) {
		sayingRepo.save(saying);
	}
	
	public Saying getLastSayingFromIp(String clientIp) {
		return sayingRepo.getLastSayingFromIp(clientIp);
	}
	
	public List<Saying> getSayingsContainingAnyGivenTags(List<Long> tagIds){
		return sayingRepo.getSayingsContainingAnyGivenTags(tagIds);
	}
	
	public List<Saying> getSayingsByText(String searchTerm){
		return sayingRepo.getSayingsByText(searchTerm);
	}

	public List<Long> getSayingsContainingExactlyGivenTags(List<Long> tagIds, String tagCount){
		return sayingRepo.getSayingsContainingExactlyGivenTags(tagIds, tagCount);
	}
	
	public List<Saying> getSayingsFromUserId(long userId){
		return sayingRepo.getSayingsFromUserId(userId);
	}
	
	public List<Saying> getSayingsFromUsername(String username){
		return sayingRepo.getSayingsFromUsername(username);
	}
	
	public List<Saying> getSayingsByTextLimited(String searchTerm, int limit){
		return sayingRepo.getSayingsByTextLimited(searchTerm, limit);
	}

	@Override
	public List<Saying> findAll() {
		return sayingRepo.findAll();
	}
	
	
	
	
	
}
