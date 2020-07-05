package com.say.say.sayings.displayStrategy;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.say.say.config.GlobalConfig;
import com.say.say.model.Saying;

public class SayingsDisplayStrategyFetchQuantity implements SayingsDisplayStrategy {

	private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);

	
	@Autowired
	GlobalConfig config;
	
	@Override
	public List<Saying> selectSayings(List<Saying> allSayings) {
		
		log.info("Selecting sayings using fetch quantity display strategy...");
		Integer fetchQuantity = null;
		try{
			String strategy = config.getProperty("sayings.displayStrategy.fetchQuantity");
			fetchQuantity = Integer.parseInt(strategy);
		}catch(NumberFormatException nfe) {
			log.error("Couldnt convert display strategy's fetch quantity from string to integer!",nfe);
			return null;
		}
		return allSayings.stream().limit(fetchQuantity).collect(Collectors.toList());
		
	}

}
