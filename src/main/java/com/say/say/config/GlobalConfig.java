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
	
	private static final String POSTING_COOLDOWN_KEY = "post_cooldown.miliseconds";
	private static final String POSTING_COOLDOWN_FORMAT_KEY = "post_cooldown.format.units";
	private static final String POSTING_COOLDOWN_UNITS_NUMBER = "post_cooldown.format.takeFirstNUnits";
	private static final String DEFAULT_AUTHENTICATION_SUCCESS_URL = "authentication.defaultSuccessURL";
	
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
	
	private String getProperty(String key) {
		return (propertyMap.get(key));
	}

	public String getPostingCooldown() {
		return getProperty(POSTING_COOLDOWN_KEY);
	}
	
	public String getPostingCooldownFormat() {
		return getProperty(POSTING_COOLDOWN_FORMAT_KEY);
	}
	
	public String getNumberOfUnitsForCooldownTimeDisplay() {
		return getProperty(POSTING_COOLDOWN_UNITS_NUMBER);
	}
	
	public String getDefaultAuthenticationSuccessUrl() {
		return getProperty(DEFAULT_AUTHENTICATION_SUCCESS_URL);
	}
	
	
	
}
