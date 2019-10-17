package com.say.say.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.say.say.util.Util;

/**
 * Class holding configuration parameters and data
 * @author gavrilo
 *
 */
public class GlobalConfig {

	private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);

	private Map<String,String> propertyMap;
	
	private static final String POSTING_COOLDOWN_KEY = "post_cooldown_miliseconds";
	
	public void init() {
		
		try {
			File globalConfig = new ClassPathResource("globalConfig.properties").getFile();
			propertyMap = Util.loadPropertiesFileIntoMap(globalConfig);
			log.info("GlobalConfig initialized!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getPostingCooldown() {
		return getProperty(POSTING_COOLDOWN_KEY);
	}

	private String getProperty(String key) {
		return (propertyMap.get(key));
	}
	
}
