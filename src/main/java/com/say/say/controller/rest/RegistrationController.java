package com.say.say.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.say.say.dao.repository.UserRepository;
import com.say.say.model.BackendMessage;
import com.say.say.model.User;
import com.say.say.service.RegistrationService;
import com.say.say.util.HttpUtil;

@RestController
@RequestMapping(path = "register/")
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(path = "user", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public List<BackendMessage> registerUser(@RequestParam(name = "username")String username,
							   @RequestParam(name = "password", required = false)String password,
							   @RequestParam(name = "email") String email,
							   @RequestParam(name = "activationCodeFromRequest", required = false) String activationCodeFromRequest) {
		

		List<BackendMessage> messages = registrationService.registerUser(username, password, email);

		return messages;
		
	}
	
}
