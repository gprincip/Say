package com.say.say.util;

import com.google.gson.Gson;
import com.say.say.model.Saying;
import com.say.say.search.SearchResult;

public class JsonUtil {

	public static Saying jsonToSaying(String json) {

		Gson gson = new Gson();
		Saying saying = gson.fromJson(json, Saying.class);
		return saying;

	}

	public static String searchResultToJson(SearchResult searchResult) {

		Gson gson = new Gson();
		String json = gson.toJson(searchResult);
		return json;
	}

}
