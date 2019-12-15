package com.say.say.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.say.say.model.UserBean;
import com.say.say.util.Util;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	UserBean userBean;
	
	@Autowired
	GlobalConfig config;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Util.setUserdata(userBean, authentication); //ERROR: username is not set to userBean for some reason
		
		proceed(request, response, authentication);
	}

	/**
	 * redirects the user to the desired url
	 * @param request
	 * @param response
	 * @param authentication
	 */
	private void proceed(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		String url = config.getDefaultAuthenticationSuccessUrl();
		try {
			redirectStrategy.sendRedirect(request, response, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
