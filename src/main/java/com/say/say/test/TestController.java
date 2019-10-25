package com.say.say.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.model.Saying;
import com.say.say.service.SayingSearcher;
import com.say.say.util.Util;

@RestController
@RequestMapping(value="/test")
public class TestController {

	@Autowired
	SayingSearcher searcher;
	
	@RequestMapping(value="/testIpExtraction")
	public void getClientIp(HttpServletRequest request) {
		System.out.println(Util.extractIpFromServletRequest(request));
	}

	/**
	 * Finds all sayings that contains specified tags
	 * @param sayingId
	 * @param tagIds comma separated tagIds
	 */
	@RequestMapping(value="/testSearch")
	public void getTestSearch(@RequestParam(value="tagIds") String tagIdsCsv) {
		
		List<Long> tagIds = Util.csvToLongs(tagIdsCsv);
		List<Saying> result = searcher.searchByTags(tagIds);
		System.out.println(result);
		
	}
	
}
