package com.say.say.sayings.displayStrategy;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisplayStrategyFactory {

	@Autowired
	SayingsDisplayStrategyFixedNumberFactory fixedNumberFactory;
	
	@Autowired
	SayingsDisplayStrategyAllFactory allFactory;
	
	private Map<DisplayStrategyEnum, SayingsDisplayStrategyFactory> factoryMap;
	
	public SayingsDisplayStrategy getDisplayStrategy(DisplayStrategyEnum strategy) {

		return factoryMap.get(strategy).create();

	}
	
	@PostConstruct
	private void init() {
		factoryMap = new HashMap<>();
		factoryMap.put(DisplayStrategyEnum.ALL, allFactory);
		factoryMap.put(DisplayStrategyEnum.FIXED_NUMBER, fixedNumberFactory);
	}

}
