package com.say.say.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.test.DatabaseInserter;

@RestController
@RequestMapping(path = "/api/util")
public class UtilController {

	/** Path to a file with list of random tag names used for testing */
	public static String TAGS_TXT_FILENAME = "static/files/corncob_lowercase.txt";

	@Autowired
	DatabaseInserter dbInserter;

	@RequestMapping(path = "/fillDbWithTags")
	public void filDBWithMockTags() {
		
		dbInserter.addTagsToDatabase(TAGS_TXT_FILENAME);
		
	}

}
