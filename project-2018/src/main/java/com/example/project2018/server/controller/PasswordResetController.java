package com.example.project2018.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project2018.server.dto.PasswordResetDTO;
import com.example.project2018.server.model.Token;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.PasswordResetTokenRepository;
import com.example.project2018.server.service.EmailService;
import com.example.project2018.server.service.UserService;

@RestController
public class PasswordResetController {

	 @Autowired
	 private UserService userService;
	 @Autowired
	 private PasswordResetTokenRepository tokenRepository;
	 @Autowired 
	 private BCryptPasswordEncoder passwordEncoder;

	
	 @ModelAttribute("passwordResetForm")
	 public PasswordResetDTO passwordReset() {
	        return new PasswordResetDTO();
	    }
	
	@RequestMapping(value="/reset-password", method = RequestMethod.GET)
	public String displayResetPasswordPage(@RequestParam(value="token",required = true) String token, HttpServletResponse httpResponse,Model model)throws Exception{
		Token resetToken = tokenRepository.findByToken(token);
		if (resetToken == null){
            //model.addAttribute("error", "Could not find password reset token.");
			return "error Could not find password reset token.";
        } else if (resetToken.isExpired()){
            //model.addAttribute("error", "Token has expired, please request a new password reset.");
        	return "Token has expired, please request a new password reset.";
        } else {
           // model.addAttribute("token", resetToken.getToken());
        	 model.addAttribute("token",  resetToken.getToken());
        	httpResponse.sendRedirect("/reset-password.html?token="+token);
        	//return "redirect:/reset-password.html/{token}";
        	// return "redirect:/reset-password.html?token=" +token;
        }
	
		
		return "ok";
	}
	
	@RequestMapping(value="/reset-password", method = RequestMethod.POST)
	public void handlePasswordReset(@RequestParam(value="token",required = false) String token,@RequestBody PasswordResetDTO passwordResetDTO,HttpServletResponse response){
		//Token tokenn = tokenRepository.findByToken(token);
		//System.out.println("token"+token);
		//User user = token.getUser();
		String updatedPassword = passwordEncoder.encode(passwordResetDTO.getPassword());
		//userService.updatePassword(updatedPassword, user.getId());
		//tokenRepository.delete(token);
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
