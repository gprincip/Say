package com.say.say.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.say.say.config.GlobalConfig;
import com.say.say.dao.repository.SayingRepository;
import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Saying;
import com.say.say.model.Tag;

@Service
public class SayingService {

	private static final Logger log = LoggerFactory.getLogger(SayingService.class);
	
	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	SayingRepository sayingRepo;
	
	@Autowired
	GlobalConfig config;
	
	public void persistSaying(String text, Set<String> tagNames, String clientIp) {
			
		Saying saying = new Saying(text, null, null, 0, clientIp, new Date());
		
		if(!validateClient(clientIp)) {
			log.warn("Client didn't pass validation! Saying will not be saved!");
			return;
		}
		
		Set<Tag> tagObjects = loadTags(tagNames);
		saying.setTags(tagObjects);
		
		sayingRepo.save(saying);
		log.info("New saying saved! info: " + saying);
		
	}

	/**
	 * Validate if client is allowed to post the saying
	 * @param clientIp
	 * @return true if client is allowed to post</br>
	 * 	       false otherwise
	 */
	private boolean validateClient(String clientIp) {
		
		Saying lastSaying = sayingRepo.getLastSayingFromIp(clientIp);
		if(lastSaying != null) {
				
			Date date = lastSaying.getDate();
			Date now = new Date();
			Long postingCooldownMillis = Long.parseLong(config.getPostingCooldown());
			
			if(date.before(now) && postingCooldownMillis != null && 
					(now.getTime() - date.getTime()) > postingCooldownMillis) {
				return true;
			}else {
				return false;
			}
		}else {
			return true;
		}

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
	
}
