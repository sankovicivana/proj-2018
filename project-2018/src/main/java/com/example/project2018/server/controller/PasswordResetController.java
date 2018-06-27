package com.example.project2018.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project2018.server.dto.PasswordResetDTO;
import com.example.project2018.server.repository.PasswordResetTokenRepository;
import com.example.project2018.server.service.EmailService;
import com.example.project2018.server.service.UserService;

public class PasswordResetController {

	 @Autowired
	 private UserService userService;
	 @Autowired
	 private PasswordResetTokenRepository tokenRepository;
	 @Autowired 
	 private BCryptPasswordEncoder passwordEncoder;

	
	
	
	@RequestMapping(value="/reset-password", method = RequestMethod.GET)
	public String displayResetPasswordPage(@RequestParam(value="token",required = true) String token){
		
		
		return "ok";
	}
	
}
