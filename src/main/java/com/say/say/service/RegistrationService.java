package com.say.say.service;

import java.util.List;

import com.say.say.model.BackendMessage;

public interface RegistrationService {

	public List<BackendMessage> registerUser(String username, String password, String email);

	public List<BackendMessage> activateUser(Long userId, String email);
	
}
