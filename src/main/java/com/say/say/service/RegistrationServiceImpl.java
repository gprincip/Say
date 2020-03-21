package com.say.say.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.say.say.config.GlobalConfig;
import com.say.say.dao.repository.UserRepository;
import com.say.say.model.BackendMessage;
import com.say.say.model.MessageType;
import com.say.say.model.User;
import com.say.say.service.email.MailSenderWrapper;

@Service
public class RegistrationServiceImpl implements RegistrationService{

	private static final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	MailSenderWrapper mailSender;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	GlobalConfig config;
	
	@Override
	public List<BackendMessage> registerUser(String username, String password, String email) {
		
		log.info("Processing registration request for: [" + username + "], [" + email + "]");
		
		List<BackendMessage> messages = new ArrayList<BackendMessage>();
		
		if(userRepo.findUserByUsername(username) != null) {
			messages.add(new BackendMessage("Username already exists", MessageType.ERROR));
			log.error("Cannot register user: username already exists!");
		}
		
		if(userRepo.findUserByEmail(email).size() > 0) {
			messages.add(new BackendMessage("Email already exists", MessageType.ERROR));
			log.error("Cannot register user: email already exists!");
		}
		
		if(isValid(messages)) {
			createNewUser(username, password, email, false, messages);
			sendActivationEmail(username, email);
		}
		
		return messages;
	}

	private boolean isValid(List<BackendMessage> messages) {
		
		for(BackendMessage msg : messages) {
			if(msg.getMessageType() == MessageType.ERROR) {
				return false;
			}
		}
		return true;
	}

	private void createNewUser(String username, String password, String email, boolean b, List<BackendMessage> messages) {
		
		User user = new User();
		user.setFirstName("Test first name");
		user.setLastName("Test last name");
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setReputation(0);
		user.setActive(false);
		user.setEmail(email);
		userRepo.save(user);
		log.info("New user saved: " + user.toString());
		messages.add(new BackendMessage("Success", MessageType.SUCCESS));
		
	}

	private void sendActivationEmail(String username, String email) {
		
		User user = userRepo.findUserByUsername(username);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setFrom(config.getProperty("mail.activationEmailFrom"));
		String activationLink = "http://localhost:8080/say/register/activate?userId=" + user.getId() + "&activationCode=" + user.getActivationCode();
		message.setText(activationLink);
		mailSender.send(message);
		log.info("Activation mail sent to: " + email);
		
	}

	@Override
	public List<BackendMessage> activateUser(Long userId, String activationCodeFromRequest) {

		List<BackendMessage> messages = new ArrayList<BackendMessage>();
		Optional<User> userOptional = userRepo.findById(userId);
		
		if(!userOptional.isPresent()) {
			messages.add(new BackendMessage("User not found!", MessageType.ERROR));
			log.error("Error processing user activation request! User not found!");
			return messages;
		}
		
		User user = userOptional.get();
		
		if (!activationCodeFromRequest.equals(user.getActivationCode())) {
			messages.add(new BackendMessage("Wrong activation code!", MessageType.ERROR));
			log.error("Wrong activation code for user activation request! Username: " + user.getUsername());
		}

		if (user.getActive() == true) {
			messages.add(new BackendMessage("User already active!", MessageType.ERROR));
			log.info("User which tried to activate is already active! Username:" + user.getUsername());
		}

		if (isValid(messages)) {

			user.setActive(true);
			userRepo.save(user);
			log.info("User " + user.getUsername() + " activated!");

		}

		return messages;

	}


}
