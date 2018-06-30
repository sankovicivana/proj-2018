package com.example.project2018.server.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.project2018.server.dto.PasswordResetDTO;

import com.example.project2018.server.model.Token;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.PasswordResetTokenRepository;

import com.example.project2018.server.service.UserService;

@RestController
public class PasswordResetController {

	 @Autowired
	 private UserService userService;
	 @Autowired
	 private PasswordResetTokenRepository tokenRepository;
	 @Autowired 
	 private BCryptPasswordEncoder passwordEncoder;

	 private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);
	
	@RequestMapping(value="/reset-password", method = RequestMethod.GET)
	public String displayResetPasswordPage(@RequestParam(value="token",required = true) String token, HttpServletResponse httpResponse,HttpSession session)throws Exception{
		Token resetToken = tokenRepository.findByToken(token);
		if (resetToken == null){
            //model.addAttribute("error", "Could not find password reset token.");
			return "error Could not find password reset token.";
        } else if (resetToken.isExpired()){
            //model.addAttribute("error", "Token has expired, please request a new password reset.");
        	return "Token has expired, please request a new password reset.";
        } else {
           // model.addAttribute("token", resetToken.getToken());
        	// model.addAttribute("token",  resetToken.getToken());
        	// HttpSession session = request.getSession();
        	 session.setAttribute("token",token); 
        	httpResponse.sendRedirect("/reset-password.html");
        	//return "redirect:/reset-password.html/{token}";
        	// return "redirect:/reset-password.html?token=" +token;
        }
	
		
		return "ok";
	}
	
	@RequestMapping(value="/reset-password", method = RequestMethod.POST)
	public void handlePasswordReset(@RequestBody PasswordResetDTO passwordResetDTO,HttpSession session){
		String token=(String) session.getAttribute("token");
		
		Token tokenn = tokenRepository.findByToken(token);
		System.out.println("token"+token);
		System.out.println("token"+tokenn);
		
		User user = tokenn.getUser();
		System.out.println("user"+user.getId());
		String password=passwordResetDTO.getPassword();
		System.out.println(password);
		String updatedPassword = passwordEncoder.encode(passwordResetDTO.getPassword());
		System.out.println("updatedPassword"+updatedPassword);
		logger.info("Korisnik: {} je promenio password. ",user.getUsername());
		userService.updatePassword(updatedPassword, user.getUsername());
		
		System.out.println("Sad cemo obrisati token");
		tokenRepository.delete(tokenn);
		
	}
}
