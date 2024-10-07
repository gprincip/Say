package com.say.say.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.say.say.model.Saying;
import com.say.say.search.SayingsSearchResult;
import com.say.say.service.RedisService;

public class JsonUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
	
	public static Saying jsonToSaying(String json) {

		Gson gson = new Gson();
		Saying saying = gson.fromJson(json, Saying.class);
		return saying;

	}
	
	public static String sayingToJson(Saying saying) {
		ObjectMapper obj = new ObjectMapper();
		try {
			return obj.writeValueAsString(saying);
		} catch (JsonProcessingException e) {
			log.error("Error converting saying (id: " + saying.getId() + ") to json", e);
			return null;
		}
	}

	public static String searchResultToJson(SayingsSearchResult searchResult) {

		Gson gson = new Gson();
		String json = gson.toJson(searchResult);
		return json;
	}

}
