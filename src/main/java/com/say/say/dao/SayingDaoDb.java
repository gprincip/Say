package com.say.say.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.say.say.model.Saying;

@Repository
public interface SayingDaoDb {

	public void save(Saying saying);
	
	public Saying getLastSayingFromIp(String clientIp);
	
	public List<Saying> getSayingsContainingAnyGivenTags(List<Long> tagIds);
	
	public List<Saying> getSayingsByText(String searchTerm);

	public List<Long> getSayingsContainingExactlyGivenTags(List<Long> tagIds, String tagCount);
	
	public List<Saying> getSayingsFromUserId(long userId);
	
	public List<Saying> getSayingsFromUsername(String username);
	
	public List<Saying> getSayingsByTextLimited(String searchTerm, int limit);

	public List<Saying> findAll();
	
}
