package com.example.project2018.server.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import com.example.project2018.server.model.Mail;
import com.example.project2018.server.model.users.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	 
	 @Autowired
	 private Environment env;
	 
	// @Autowired
	// private SpringTemplateEngine templateEngine;
	 
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
	 
	 public void sendMailForgot(Mail mail) {
		 
		 
		 
	       /* try {
	            MimeMessage message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message,
	                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                    StandardCharsets.UTF_8.name());

	           // Context context = new Context();
	           // context.setVariables(mail.getModel());
	            //String html = templateEngine.process("email/email-template", context);

	            helper.setTo(mail.getTo());
	            helper.setText("ddd", true);
	            helper.setSubject(mail.getSubject());
	            helper.setFrom(mail.getFrom());

	            javaMailSender.send(message);
	        } catch (Exception e){
	            throw new RuntimeException(e);
	        }*/
	    }
	 
}
