package com.say.say.sayings.displayStrategy;

import java.util.List;

import com.say.say.model.Saying;

/**
 * Strategy used to select subset of all sayings from one user that will be displayed on view.
 * @author gavrilo
 *
 */
public interface SayingsDisplayStrategy {

	List<Saying> selectSayings(List<Saying> allSayings);
	
}
