package com.example.project2018.server.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.project2018.server.model.users.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	 
	 @Autowired
	 private Environment env;
	 
	 
	 
	 @Async
	 public void sentMail(User user){
		 
		 SimpleMailMessage mail = new SimpleMailMessage();
	     String email = user.getEmail();
	     //encoded
	     String encoded = Base64.getEncoder().encodeToString(email.getBytes());
	     mail.setTo(user.getEmail());
	     mail.setFrom(env.getProperty("spring.mail.username"));
	     mail.setSubject("Account Activation");
	     mail.setText("Hi! " + user.getFirstname() + " " + user.getLastname()+",\n\n You must follow this link to activate your account:\n\n http://localhost:8080/index.html?encoded="+ encoded +"\n\n");
	     javaMailSender.send(mail);
		 
	 }
	 
}
