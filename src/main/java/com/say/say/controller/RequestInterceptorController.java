package com.say.say.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.say.say.model.LoggedUser;
import com.say.say.util.Util;

@Component
public class RequestInterceptorController extends HandlerInterceptorAdapter{

	@Autowired
	LoggedUser user;
	
	/**
	 * Called before every request. Can be used for setting session scoped user data
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    
		Util.setUserdata(user, request);
		
	    return true;
	}
	
}
