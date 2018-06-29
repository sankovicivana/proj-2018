package com.example.project2018.server.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.example.project2018.server.model.Mail;
import com.example.project2018.server.model.Token;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.PasswordResetTokenRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	 @Autowired 
	 private PasswordResetTokenRepository tokenRepository;
	 @Autowired
	 private Environment env;
	 
	 @Autowired
	 private SpringTemplateEngine templateEngine;
	 
	 @Async
	 public void sentMail(User user){
		 	Mail mail = new Mail();
	        mail.setFrom("timalek3@gmail.com");
	        mail.setTo(user.getEmail());
	        mail.setSubject("Account Activation");
		 
	        Token token = new Token();
	        token.setToken(UUID.randomUUID().toString());
	        token.setUser(user);
	        token.setExpiryDate(30);
	        tokenRepository.save(token);
	        String link="http://localhost:8080/index.html?encoded="+token.getToken();
	        String mailString="Hi! "+ user.getFirstname()+" "+ user.getLastname();
	         mailString+="<div>You must follow this link to activate your account:  <a href ="+link+ ">"
	                + "<u>link</u></a>.</div>";
	        try {
	        MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
            StandardCharsets.UTF_8.name());

         
            message.setContent(mailString, "text/html");
            helper.setTo(mail.getTo());
          helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            javaMailSender.send(message);
	        } catch (Exception e){
	            throw new RuntimeException(e);
	        }
		 
		// SimpleMailMessage mail = new SimpleMailMessage();
	   //  String email = user.getEmail();
	     //encoded
	    // String encoded = Base64.getEncoder().encodeToString(email.getBytes());
	    // Token token = new Token();
	    //    token.setToken(UUID.randomUUID().toString());
	    //    token.setUser(user);
	   //     token.setExpiryDate(30);
	    //    tokenRepository.save(token);
	   //  String link="http://localhost:8080/index.html?encoded="+token.getToken();
	     
	   //  mail.setTo(user.getEmail());
	   //  mail.setFrom(env.getProperty("spring.mail.username"));
	  //   mail.setSubject("Account Activation");
	  //   mail.setText("Hi! " + user.getFirstname() + " " + user.getLastname()+",\n\n You must follow this link to activate your account:\n\n http://localhost:8080/index.html?encoded="+ token.getToken()+"\n\n");
	    // mail.setText("Hi! " + user.getFirstname() + " " + user.getLastname()+",\n\n You must follow this link to activate your account:\n\n"+" <a href ="+link+ ">"  + "<u>link</u></a>.\n\n");
	    
	     
	 //    javaMailSender.send(mail);
		 
	 }
	 
	 @Async
	 public void sendMailForgot(Mail mail,String send) {
	
	       try {
	            MimeMessage message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message,
	                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                    StandardCharsets.UTF_8.name());

	          //Context context = new Context();
	          //context.setVariables(mail.getModel());
	         // String html = templateEngine.process("email/email-template", context);
	            message.setContent(send, "text/html");
	            helper.setTo(mail.getTo());
	            //helper.setText(send, true);
	           // mail.setText("Hi! " + user.getFirstname() + " " + user.getLastname()+",\n\n You must follow this link to activate your account:\n\n http://localhost:8080/index.html?encoded="+ encoded +"\n\n");
	            helper.setSubject(mail.getSubject());
	            helper.setFrom(mail.getFrom());

	            javaMailSender.send(message);
	        } catch (Exception e){
	            throw new RuntimeException(e);
	        }
	    }
	 
}
