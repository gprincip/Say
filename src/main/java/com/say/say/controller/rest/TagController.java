package com.say.say.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Tag;

@RestController
@RequestMapping(path="/api/tag")
public class TagController {

	@Autowired
	TagRepository tagRepo;
	
	@RequestMapping(path="/findAll")
	public List<Tag> findAll(){
		return tagRepo.findAll();
	}
	
}
