package com.say.say.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.model.Saying;
import com.say.say.search.ISearcher;
import com.say.say.search.SearchResult;
import com.say.say.service.SayingSearcher;
import com.say.say.service.SayingService;
import com.say.say.util.JsonUtil;
import com.say.say.util.Util;

@RestController
@RequestMapping(value="/test")
public class TestController {

	@Autowired
	SayingSearcher searcher;
	
	@Autowired
	ISearcher dbSearcher;
	
	@Autowired
	SayingService sService;
	
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
		List<Saying> result = searcher.searchSayingsContainingAnyGivenTags(tagIds);
		System.out.println(result);
		
	}
	
	@RequestMapping(value="/testSearch2")
	public List<Long> getSayingsWithExactTags(@RequestParam(value="tagIds") String tagIdsCsv) {
		
		List<Long> tagIds = Util.csvToLongs(tagIdsCsv);
		List<Long> result = searcher.searchSayingsContainingExactlyGivenTags(tagIds);
		return result;
	}
	
	@RequestMapping(value="/search/sText")
	public String sayingsByText(@RequestParam(value="searchTerm") String searchTerm, @RequestParam(value="fetchQuantity") Integer fetchQuantity) {
		SearchResult result = dbSearcher.search(searchTerm, fetchQuantity, null);
		return JsonUtil.searchResultToJson(result);
	}
	
	@RequestMapping(value="/search/sText2")
	public List<Saying> sayingsByText2(@RequestParam(value="searchTerm") String searchTerm) {
		
		return sService.getSayingsByTextLimited("q", 4);
		
	}
}
