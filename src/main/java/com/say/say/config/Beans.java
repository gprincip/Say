package com.say.say.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.say.say.model.UserBean;

@Configuration
public class Beans {

	@Bean
	public GlobalConfig getGlobalConfig() {
		GlobalConfig config = new GlobalConfig();
		config.init();
		return config;
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UserBean sessionScopedBean() {
	    return new UserBean();
	}
}
