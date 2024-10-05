package com.say.say.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Search parameters used for searching for sayings
 * @author gavrilo
 *
 */
public class SayingsSearchParameters implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String searchTerm;
	private int resultsCountLimit;
	
	
	
	public SayingsSearchParameters(String searchTerm, int resultsCountLimit) {
		super();
		this.searchTerm = searchTerm;
		this.resultsCountLimit = resultsCountLimit;
	}
	public SayingsSearchParameters() {
		// TODO Auto-generated constructor stub
	}
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public int getResultsCountLimit() {
		return resultsCountLimit;
	}
	public void setResultsCountLimit(int resultsCountLimit) {
		this.resultsCountLimit = resultsCountLimit;
	}
	@Override
	public int hashCode() {
		return Objects.hash(resultsCountLimit, searchTerm);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SayingsSearchParameters other = (SayingsSearchParameters) obj;
		return resultsCountLimit == other.resultsCountLimit && Objects.equals(searchTerm, other.searchTerm);
	}
	@Override
	public String toString() {
		return "SayingsSearchParameters [searchTerm=" + searchTerm + ", resultsCountLimit=" + resultsCountLimit + "]";
	}

	
}
