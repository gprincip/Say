package com.say.say.model;

import java.io.Serializable;

/**
 * Search parameters used for searching for sayings
 * @author gavrilo
 *
 */
public class SayingsSearchParameters implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String searchTerm;
	private int fetchQuantity;
	
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public int getFetchQuantity() {
		return fetchQuantity;
	}
	public void setFetchQuantity(int fetchQuantity) {
		this.fetchQuantity = fetchQuantity;
	}
	
	public SayingsSearchParameters() {}
	
	public SayingsSearchParameters(String searchTerm, int fetchQuantity) {
		this.searchTerm = searchTerm;
		this.fetchQuantity = fetchQuantity;
	}
	@Override
	public String toString() {
		return "SayingsSearchParameters [searchTerm=" + searchTerm + ", fetchQuantity=" + fetchQuantity + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fetchQuantity;
		result = prime * result + ((searchTerm == null) ? 0 : searchTerm.hashCode());
		return result;
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
		if (fetchQuantity != other.fetchQuantity)
			return false;
		if (searchTerm == null) {
			if (other.searchTerm != null)
				return false;
		} else if (!searchTerm.equals(other.searchTerm))
			return false;
		return true;
	}
	
}
