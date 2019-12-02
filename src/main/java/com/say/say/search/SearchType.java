package com.say.say.search;

/**
 * Enum for defining which elements will be searched for by the ISearcher. </br>
 * Search prefix is the prefix used to define the type of results that came from </br>
 * external source.
 * @author gavrilo
 *
 */
public enum SearchType {
	
	SAYING_BY_TEXT("sText"),
	SAYING_BY_TAGS("sTags"),
	TAG_BY_NAME("tn"),
	AUTHOR_BY_NAME("an");
	
	String searchPrefix;
	
	SearchType(String searchPrefix) {
		this.searchPrefix = searchPrefix;
	}

	public String getSearchPrefix() {
		return searchPrefix;
	}

	public void setSearchPrefix(String searchPrefix) {
		this.searchPrefix = searchPrefix;
	}
	
}
