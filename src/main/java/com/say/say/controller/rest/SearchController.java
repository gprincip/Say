package com.say.say.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.model.SayingsSearchParameters;
import com.say.say.search.ISearcher;
import com.say.say.search.SayingsSearchResult;
import com.say.say.util.JsonUtil;

@RestController
@RequestMapping(path = "/search")
public class SearchController {

	@Autowired
	ISearcher dbSearcher;
	
	@RequestMapping(value="/sText")
	public String sayingsByText(@RequestParam(value="searchTerm") String searchTerm, @RequestParam(value="fetchQuantity") Integer fetchQuantity) {
		
		SayingsSearchParameters params = new SayingsSearchParameters();
		params.setSearchTerm(searchTerm);
		params.setFetchQuantity(fetchQuantity);
		
		SayingsSearchResult result = dbSearcher.searchSayingsByText(params);
		return JsonUtil.searchResultToJson(result);
	}
	
}
