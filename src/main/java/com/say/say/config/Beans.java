package com.say.say.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import com.say.say.model.UserBean;
import com.say.say.search.DBSearcher;
import com.say.say.search.ISearcher;
import com.say.say.service.RegistrationService;
import com.say.say.service.RegistrationServiceImpl;

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
	
	@Bean
	@Primary
	public ISearcher getSearcher() {
		return new DBSearcher();
	}
	
	@Bean
	public RegistrationService registrationService() {
		return new RegistrationServiceImpl();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
