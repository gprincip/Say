package com.say.say.search;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.say.say.dao.repository.SayingRepository;
import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Saying;
import com.say.say.model.SayingsSearchParameters;
import com.say.say.service.SayingService;

@Component
public class DBSearcher implements ISearcher{

	private static final Logger log = LoggerFactory.getLogger(DBSearcher.class);

	@Autowired
	SayingRepository sayingRepo;
	
	@Autowired
	SayingService sayingService;
	
	@Autowired
	TagRepository tagRepo;
	
	@Override
	public SayingsSearchResult searchSayingsByText(SayingsSearchParameters searchParameters) {
		
		String searchTerm = searchParameters.getSearchTerm();
		int fetchQuantity = searchParameters.getFetchQuantity();
		
		List<Saying> sayingsContainingText = sayingService.getSayingsByTextLimited(searchTerm, fetchQuantity);
		SayingsSearchResult result = new SayingsSearchResult();
		result.setSearchType(SearchType.SAYING_BY_TEXT.getSearchPrefix());
		result.setSearchTerm(searchTerm);
		JsonObject searchResult = new JsonObject();
		JsonArray objectsDataArray = new JsonArray();
		
		for(Saying saying : sayingsContainingText) {
			
			JsonObject id = new JsonObject();		
			id.addProperty("id", saying.getId());
			id.addProperty("label", saying.getText());
			objectsDataArray.add(id);
			
		}

		searchResult.add("results", objectsDataArray);		
		result.setJsonData(searchResult);
		
		log.info("Returning results for: " + searchTerm);
		
		return result;
	}

}
