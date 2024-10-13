package com.say.say.sayings.displayStrategy;

import org.springframework.stereotype.Component;

@Component
public class SayingsDisplayStrategyFixedNumberFactory implements SayingsDisplayStrategyFactory{

	@Override
	public SayingsDisplayStrategy create() {
		return new SayingsDisplayStrategyFixedNumber();
	}

}
