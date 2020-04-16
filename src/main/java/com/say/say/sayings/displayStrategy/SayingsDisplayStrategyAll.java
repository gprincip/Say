package com.say.say.sayings.displayStrategy;

import java.util.List;

import com.say.say.model.Saying;

/**
 * This sayings display strategy selects all sayings from the user
 * @author gavrilo
 *
 */
public class SayingsDisplayStrategyAll implements SayingsDisplayStrategy{

	@Override
	public List<Saying> selectSayings(List<Saying> allSayings) {
		
		return allSayings;
	}
}
