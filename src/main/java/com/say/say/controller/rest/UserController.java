package com.say.say.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.model.UserBean;

@RestController
@RequestMapping(path = "/userController")
public class UserController {

	@Autowired
	UserBean userBean;
	
	@RequestMapping(path = "/getLoggedInUsername", method = RequestMethod.GET)
	public String getLoggedInUsername() {
		return userBean.getUsername();
	}
	
}
