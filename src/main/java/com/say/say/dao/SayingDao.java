package com.say.say.dao;

import java.util.List;

import com.say.say.model.Saying;

public interface SayingDao{

	public void save(Saying saying);
	
	public List<Saying> findAll();
	
}
