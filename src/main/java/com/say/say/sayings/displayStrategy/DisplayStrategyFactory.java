package com.say.say.sayings.displayStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisplayStrategyFactory {

	@Autowired
	SayingsDisplayStrategyFixedNumberFactory fixedNumberFactory;
	
	@Autowired
	SayingsDisplayStrategyAllFactory allFactory;
	
	public SayingsDisplayStrategy getDisplayStrategy(DisplayStrategyEnum strategy) {

		switch (strategy) {
			case ALL:
				return allFactory.create();
			case FIXED_NUMBER:
				return fixedNumberFactory.create();
			default:
				return null;
		}

	}

}
