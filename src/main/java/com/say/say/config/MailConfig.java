package com.say.say.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Autowired
	ApplicationProperties config;
	
	@Bean(name="mailSender")
    public MailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(config.getMailSenderHost());
        javaMailSender.setPort(config.getMailSenderPort());
        javaMailSender.setProtocol(config.getMailSenderProtocol());
        javaMailSender.setUsername(config.getMailSenderUsername());
        javaMailSender.setPassword(config.getMailSenderPassword());
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", config.getMailSenderSmtpAuthProperty());
        mailProperties.put("mail.smtp.starttls.enable", config.getMailSenderSmtpEnableStarttlsProperty());
        mailProperties.put("mail.smtp.debug", config.getMailSenderSmtpDebugProperty());
        mailProperties.put("mail.smtp.ssl.trust", config.getMailSenderSmtpSSlTrustProperty());
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }
	
}
