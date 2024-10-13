package com.say.say.sayings.displayStrategy;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.say.say.config.ApplicationProperties;
import com.say.say.model.Saying;

public class SayingsDisplayStrategyFixedNumber implements SayingsDisplayStrategy {

	private static final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

	
	@Autowired
	ApplicationProperties config;
	
	@Override
	public List<Saying> selectSayings(List<Saying> allSayings) {
		
		log.info("Selecting sayings using fetch quantity display strategy...");
		Integer numberInt = null;
		try{
			String numberStr = config.getProperty("sayings.displayStrategy.fixedNumber.number");
			numberInt = Integer.parseInt(numberStr);
		}catch(NumberFormatException nfe) {
			log.error("Couldnt convert display strategy's fixed number parameter from string to integer!",nfe);
			return null;
		}
		return allSayings.stream().limit(numberInt).collect(Collectors.toList());
		
	}

}
