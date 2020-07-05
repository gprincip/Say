package com.say.say.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.mock.MockDataAddingService;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class); 
	
	@Autowired
	MockDataAddingService mockDataAddingService;
	
	@RequestMapping(path = "/insertMockData")
	public void insertMockDataToDatabase() {
		log.info("Inserting mock data to database!");
		mockDataAddingService.insertDataToDatabase();
	}
	
	@RequestMapping(path = "/insertMockUsers")
	public void insertMockUsersToDatabase() {
		log.info("Inserting mock users to database!");
		mockDataAddingService.addUsersAndRoles();
	}
	
	@RequestMapping(path = "/insertMockSayings")
	public void insertMockSayingsToDatabase() {
		log.info("Inserting mock Sayings to database!");
		mockDataAddingService.addSayings();
	}
	
	@RequestMapping(path = "/insertMockTags")
	public void insertMockTagsToDatabase() {
		log.info("Inserting mock tagas to database!");
		mockDataAddingService.addTags();
	}
	
}
