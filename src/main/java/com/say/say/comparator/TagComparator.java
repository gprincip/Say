package com.say.say.comparator;

import java.util.Comparator;

import com.say.say.model.Tag;

public class TagComparator implements Comparator<Tag>{

	@Override
	public int compare(Tag t1, Tag t2) {
		return t1.getName().compareTo(t2.getName());
	}


}
