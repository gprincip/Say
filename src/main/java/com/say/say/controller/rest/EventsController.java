package com.say.say.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.events.producers.SayingEventsManager;
import com.say.say.model.Saying;

@RestController
@RequestMapping(path = "/api/rest/v1/")
public class EventsController {

	@Autowired
	SayingEventsManager sayingEventManager;
	
	@RequestMapping(path = "/generateSayingCreatedEvent", consumes={"application/json"}, method=RequestMethod.POST)
	public void generateSayingCreatedEvent(@RequestBody Saying saying) {
		
		sayingEventManager.generateSayingCreatedEvent(saying);
		
	}
	
}
