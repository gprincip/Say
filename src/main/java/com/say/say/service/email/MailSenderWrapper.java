package com.say.say.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.say.say.config.ApplicationProperties;

/**
 * Email sender wrapper intended (in the moment of implementation)
 * to separate email sending logic in dev and prod environment.
 * @author gavrilo
 *
 */
@Component
public class MailSenderWrapper {

	private static final Logger log = LoggerFactory.getLogger(MailSenderWrapper.class);
	
	@Autowired
	MailSender mailSender;
	
	@Autowired
	ApplicationProperties config;
	
	public void send(SimpleMailMessage message) {
		
		Boolean devMode = Boolean.parseBoolean(config.getProperty("mailSender.developmentMode"));
		
		if(!devMode) {
			mailSender.send(message);
		}else {
			String from = message.getFrom();
			String to = getRecipients(message.getTo());
			String text = message.getText();
			String subject = message.getSubject();
			
			log.info("\nMail sent in development mode: \nfrom: " + from
					+ " to: " + to + " subject: " + subject + " text: " + text + "\n");
			
		}
		
	}

	/**
	 * Transform the array of recipients to csv of recipients
	 * @param to
	 * @return
	 */
	private String getRecipients(String[] recipients) {
		String csvRecipients = "";
		for(String str : recipients) {
			csvRecipients += (str + ",");
		}
		csvRecipients.substring(0, csvRecipients.length()-1);
		return csvRecipients;
	}
	
}
