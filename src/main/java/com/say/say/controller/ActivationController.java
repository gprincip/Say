package com.say.say.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.say.say.model.BackendMessage;
import com.say.say.model.MessageType;
import com.say.say.service.RegistrationService;

@Controller
public class ActivationController {
	
	@Autowired
	RegistrationService registrationService;
	
	@RequestMapping(path = "register/activate", method = RequestMethod.GET)
	public ModelAndView activateUser(@RequestParam(name = "userId") Long userId, @RequestParam(name = "activationCode") String activationCode) {

		ModelAndView mav = new ModelAndView();

		List<BackendMessage> messages = registrationService.activateUser(userId, activationCode);

		mav.setViewName("afterActivation");

		if(isValid(messages)) {
			messages.add(new BackendMessage("User successfully activated!", MessageType.SUCCESS));
		}
		
		mav.addObject("messages", messages);

		return mav;
	}
	
	
	private boolean isValid(List<BackendMessage> messages) {
		
		for(BackendMessage msg : messages) {
			if(msg.getMessageType() == MessageType.ERROR) {
				return false;
			}
		}
		return true;
	}
}
