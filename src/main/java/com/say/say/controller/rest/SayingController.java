package com.say.say.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.dao.repository.SayingRepository;
import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Saying;
import com.say.say.model.Tag;

@RestController
@RequestMapping(path="/api/saying")
public class SayingController {

	@Autowired
	SayingRepository sayingRepo;
	
	@Autowired
	TagRepository tagRepo;
	
	@RequestMapping(path="/testSave")
	public void testSave() {
		Saying s = new Saying("To be or not to be", "Pera", null, 0);
		
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(new Tag("Sport"));
		tags.add(new Tag("Formula 1"));
		
		for(Tag t : tags) {
			tagRepo.save(t);
		}
		
		s.setTags(tags);
		sayingRepo.save(s);
		System.out.println("Saved: " + s);
	}
	
	@RequestMapping(path="/findAll")
	public List<Saying> findAll(){
		return sayingRepo.findAll();
	}
	
}
