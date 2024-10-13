package com.say.say.sayings.displayStrategy;

import org.springframework.stereotype.Component;

@Component
public class SayingsDisplayStrategyAllFactory implements SayingsDisplayStrategyFactory{

	@Override
	public SayingsDisplayStrategy create() {
		return new SayingsDisplayStrategyAll();
	}

}
