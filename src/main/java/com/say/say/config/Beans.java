package com.say.say.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import com.say.say.events.consumers.SayingEventsConsumer;
import com.say.say.events.consumers.SayingEventsConsumerImpl;
import com.say.say.model.LoggedUser;
import com.say.say.sayings.displayStrategy.DisplayStrategyEnum;
import com.say.say.sayings.displayStrategy.DisplayStrategyFactory;
import com.say.say.sayings.displayStrategy.SayingsDisplayStrategy;
import com.say.say.sayings.displayStrategy.SayingsDisplayStrategyAll;
import com.say.say.sayings.displayStrategy.SayingsDisplayStrategyFixedNumber;
import com.say.say.search.DBSearcher;
import com.say.say.search.ISearcher;
import com.say.say.search.RedisSearcher;
import com.say.say.service.FileSystemStorageService;
import com.say.say.service.RegistrationService;
import com.say.say.service.RegistrationServiceImpl;
import com.say.say.service.StorageService;
import com.say.say.sql.SqlExecutorService;
import com.say.say.sql.SqlExecutorServiceImpl;

@Configuration
public class Beans {
	
	@Bean
	public ApplicationProperties getGlobalConfig() {
		ApplicationProperties config = new ApplicationProperties();
		config.init();
		return config;
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public LoggedUser sessionScopedBean() {
	    return new LoggedUser();
	}
	
	@Bean
	@Primary
	public ISearcher getDbSearcher() {
		return new DBSearcher();
	}
	
	@Bean
	public ISearcher getRedisSearcher() {
		return new RedisSearcher();
	}
	
	@Bean
	public RegistrationService registrationService() {
		return new RegistrationServiceImpl();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SqlExecutorService sqlExecutorService() {
		return new SqlExecutorServiceImpl();
	}
	
	@Autowired
	DisplayStrategyFactory displayStrategyFactory;
	
	@Bean
	public SayingsDisplayStrategy getSayingsDisplayStrategy() {
		
		ApplicationProperties config = new ApplicationProperties(); //we must use new instance of config here, because it would still not be injected into this class when we would use it
		config.init();
		
		String strategy = config.getProperty("sayings.displayStrategy.usedDisplayStrategy");
		return displayStrategyFactory.getDisplayStrategy(DisplayStrategyEnum.valueOf(strategy));
	}
	
	@Bean
	public StorageService storageService() {
		return new FileSystemStorageService();
	}
	
	@Bean
	public SayingEventsConsumer sayingEventConsumer() {
		SayingEventsConsumer consumer = new SayingEventsConsumerImpl();
		new Thread(() -> consumer.consumeSayings()).start();
		return consumer;
	}
	
}
