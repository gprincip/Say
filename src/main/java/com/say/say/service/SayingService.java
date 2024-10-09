package com.say.say.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.say.say.config.ApplicationProperties;
import com.say.say.dao.SayingDaoDb;
import com.say.say.dao.SayingDaoRedis;
import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Saying;
import com.say.say.model.Tag;

@Service
public class SayingService {

	private static final Logger log = LoggerFactory.getLogger(SayingService.class);
	
	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	SayingDaoDb sayingDao;
	
	@Autowired
	SayingDaoRedis sayingRedisDao;
	
	@Autowired
	ApplicationProperties config;
	
	public void persistSaying(Saying saying, String clientIp) {
			
		saying.setClientIp(clientIp);
		saying.setDate(new Date());
		
		Set<Tag> tagObjects = loadTags(saying.getTagNames());
		saying.setTags(tagObjects);
		
		sayingDao.save(saying);
		sayingRedisDao.saveUserLastPostTimestamp();
		
		log.info("New saying saved! info: " + saying);
		
	}

	/**
	 * Persists tags in database if doesn't exists, or loads existing tags by tag name
	 * @param tags
	 * @return Set of tags that were loaded from database or now created and persisted
	 */
	private Set<Tag> loadTags(Set<String> tagNames) {
		
 		Set<Tag> tags = new HashSet<Tag>();
		
		for(String tagname : tagNames) {
		
			Tag tag = tagRepo.findTagByName(tagname);
			
			//save tag if it doesn't exist
			if(tag == null) {
				tag = new Tag(tagname);
				tag = tagRepo.save(tag);
				log.info("New tag saved! info: " + tag);
			}
			
			tags.add(tag);
			
		}
		
		return tags;
		
	}
	
	/**
	 * Returns list of sayings limited by number
	 * @param limit max number of sayings that will be returned by query
	 * @param id of the
	 * @return
	 */
	public List<Saying> getSayingsByTextLimited(String searchTerm, int limit) {
		
		List<Saying> results = sayingDao.getSayingsByTextLimited(searchTerm, limit);
		
		return results;
	}

	public void addSayingToUserSayingsCache(Saying saying, Long id) {
		
		sayingRedisDao.addSayingToUserSayingsCache(saying, id);
		
	}
	
}

