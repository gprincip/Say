package com.say.say.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.say.say.util.Util;

/**
 * Class holding configuration parameters and data
 * @author gavrilo
 *
 */
public class ApplicationProperties {

	private static final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

	private Map<String,String> propertyMap;
	
	private static final String POSTING_COOLDOWN_KEY = "post_cooldown.miliseconds";
	private static final String POSTING_COOLDOWN_FORMAT_KEY = "post_cooldown.format.units";
	private static final String POSTING_COOLDOWN_UNITS_NUMBER = "post_cooldown.format.takeFirstNUnits";
	private static final String DEFAULT_AUTHENTICATION_SUCCESS_URL = "authentication.defaultSuccessURL";
	private static final String MAIL_SENDER_HOST_KEY = "mailSender.config.host";
	private static final String MAIL_SENDER_PORT_KEY = "mailSender.config.port";
	private static final String MAIL_SENDER_PROTOCOL_KEY = "mailSender.config.protocol";
	private static final String MAIL_SENDER_USERNAME_KEY = "mailSender.config.username";
	private static final String MAIL_SENDER_PASSWORD_KEY = "mailSender.config.password";
	private static final String MAIL_SENDER_PROPERTIES_SMTP_AUTH_KEY = "mailSender.properties.mail.smtp.auth";
	private static final String MAIL_SENDER_PROPERTIES_SMTP_ENABLE_STARTTLS_KEY = "mailSender.properties.mail.smtp.starttls.enable";
	private static final String MAIL_SENDER_PROPERTIES_SMTP_DEBUG_KEY = "mailSender.properties.mail.smtp.debug";
	private static final String MAIL_SENDER_PROPERTIES_SMTP_SSL_TRUST_KEY = "mailSender.properties.mail.smtp.ssl.trust";
	
	public void init() {
		
		try {
			File globalConfig = new ClassPathResource("globalConfig.properties").getFile();
			propertyMap = Util.loadPropertiesFileIntoMap(globalConfig);
			log.info("GlobalConfig initialized!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getProperty(String key) {
		return (propertyMap.get(key));
	}

	public String getPostingCooldown() {
		return getProperty(POSTING_COOLDOWN_KEY);
	}
	
	public String getPostingCooldownFormat() {
		return getProperty(POSTING_COOLDOWN_FORMAT_KEY);
	}
	
	public String getNumberOfUnitsForCooldownTimeDisplay() {
		return getProperty(POSTING_COOLDOWN_UNITS_NUMBER);
	}
	
	public String getDefaultAuthenticationSuccessUrl() {
		return getProperty(DEFAULT_AUTHENTICATION_SUCCESS_URL);
	}
	
	public String getMailSenderHost() {
		return getProperty(MAIL_SENDER_HOST_KEY);
	}
	
	public Integer getMailSenderPort() {
		String portStr = getProperty(MAIL_SENDER_PORT_KEY);
		return Integer.parseInt(portStr);
	}
	
	public String getMailSenderProtocol() {
		return getProperty(MAIL_SENDER_PROTOCOL_KEY);
	}
	
	public String getMailSenderUsername() {
		return getProperty(MAIL_SENDER_USERNAME_KEY);
	}
	
	public String getMailSenderPassword() {
		return getProperty(MAIL_SENDER_PASSWORD_KEY);
	}
	
	public String getMailSenderSmtpAuthProperty() {
		return getProperty(MAIL_SENDER_PROPERTIES_SMTP_AUTH_KEY);
	}
	
	public String getMailSenderSmtpEnableStarttlsProperty() {
		return getProperty(MAIL_SENDER_PROPERTIES_SMTP_ENABLE_STARTTLS_KEY);
	}
	
	public String getMailSenderSmtpDebugProperty() {
		return getProperty(MAIL_SENDER_PROPERTIES_SMTP_DEBUG_KEY);
	}
	
	public String getMailSenderSmtpSSlTrustProperty() {
		return getProperty(MAIL_SENDER_PROPERTIES_SMTP_SSL_TRUST_KEY);
	}
	
}
