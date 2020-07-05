package com.say.say.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationSuccessHandlerImpl successHandler;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	DataSource dataSource;
	
	public static String[] roles = {"USER", "ADMIN"};
	
	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * 
	 * auth.inMemoryAuthentication()
	 * .withUser("user").password(passwordEncoder.encode("password")).roles("USER")
	 * //noop defines that no password encoder is used .and()
	 * .withUser("admin").password(passwordEncoder.encode("password")).roles(
	 * "ADMIN") .and().passwordEncoder(passwordEncoder);
	 * 
	 * }
	 */
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource) 
				.passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username, password, active from user where username=?")
				.authoritiesByUsernameQuery("select u.username, r.role from user_role ur \n" + 
						"join user u on u.id = ur.user_id \n" + 
						"join role r on r.id = ur.role_id \n" + 
						"where u.username = ?");
	}
	
	 protected void configure(HttpSecurity http) throws Exception {
		    http.authorizeRequests()
		    .antMatchers("/sayings/**").hasAnyRole(roles)
		    .antMatchers("/admin/**").hasAnyRole(roles)
		    .antMatchers("/api/**").permitAll()
		    .and()
		    .formLogin()
		    .successHandler(successHandler)
		    .loginPage("/login")
		    .and()
		    .csrf().disable();
		    
		}
}
