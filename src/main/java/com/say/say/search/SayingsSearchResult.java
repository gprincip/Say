package com.say.say.search;

import java.io.Serializable;

import com.google.gson.JsonObject;

public class SayingsSearchResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String searchTerm;
	private String searchType;
	private JsonObject jsonData;
	
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public JsonObject getJsonData() {
		return jsonData;
	}
	public void setJsonData(JsonObject jsonData) {
		this.jsonData = jsonData;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
}
