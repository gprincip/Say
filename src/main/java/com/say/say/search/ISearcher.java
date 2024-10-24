package com.say.say.search;

import com.say.say.model.SayingsSearchParameters;

/**
 * Interface used as a base specification of functionalities for </br>
 * obtaining search results from external source. For example we </br>
 * can use this to implement search mechanism to obtain data from DB</br>
 * and after that we can switch to search engine by simply implementing</br>
 * new searcher which will deliver data and the rest of the logic </br>
 * will be the same.
 * @author gavrilo
 *
 */
public interface ISearcher {

	public SayingsSearchResult searchSayingsByText(SayingsSearchParameters searchParameters);
	
}
