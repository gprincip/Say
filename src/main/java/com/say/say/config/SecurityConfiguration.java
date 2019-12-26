package com.say.say.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationSuccessHandlerImpl successHandler;
	
	public static String[] roles = {"USER", "ADMIN"};
	
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) 
	      throws Exception {
		
		  auth.inMemoryAuthentication()
		  .withUser("user").password("{noop}password").roles("USER") //noop defines that no password encoder is used
		  .and()
		  .withUser("admin").password("{noop}password").roles("ADMIN");
		 
	    }
	
	 protected void configure(HttpSecurity http) throws Exception {
		    http.authorizeRequests()
		    .antMatchers("/sayings").hasAnyRole(roles)
		    .antMatchers("/api/**").permitAll()
		    .and()
		    .formLogin()
		    .successHandler(successHandler)
		    .and()
		    .csrf().disable();
		    
		}
}
