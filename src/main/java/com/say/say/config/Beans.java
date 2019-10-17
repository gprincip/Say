package com.say.say.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

	@Bean
	public GlobalConfig getGlobalConfig() {
		GlobalConfig config = new GlobalConfig();
		config.init();
		return config;
	}
	
}
