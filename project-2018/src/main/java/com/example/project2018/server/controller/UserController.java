package com.example.project2018.server.controller;

import java.awt.PageAttributes.MediaType;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.exception.AuthenticationException;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.UserRepository;
import com.example.project2018.server.security.JwtTokenUtil;
import com.example.project2018.server.security.JwtUser;
import com.example.project2018.server.service.EmailService;
import com.example.project2018.server.service.UserService;


@RestController
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
    
    
  //registracija usera
    @RequestMapping(value="/register",method=RequestMethod.POST)
    public ResponseEntity<User> registrate(@RequestBody UserDTO userDTO){
 
    	if ((userRepository.findByUsername(userDTO.getUsername()) != null)|| (userRepository.findByEmail(userDTO.getEmail()) != null) ) {
             return new ResponseEntity<>(HttpStatus.FORBIDDEN);
         }
    	
    	
    	System.out.println("Ovde"+userDTO.getEmail());
    	User user=userRepository.findByEmail(userDTO.getEmail());
    	System.out.println("Ovde"+userDTO.getEmail());
    	if(user==null)
    	{
    		try
    		{
    			User user1=userService.createUser(userDTO);
    			
    			emailService.sentMail(user1);
    			return new ResponseEntity<User>(HttpStatus.OK);
    		} catch (Exception e) 
    		{
    			e.printStackTrace();
    			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    		}
    	}
    	return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }
    
    
    @RequestMapping(
            value = "/confirmation/{encoded}",
            method = RequestMethod.GET)
    public ResponseEntity<User> sentMail(@PathVariable String encoded){
    	 byte[] bytesDec = Base64.decodeBase64(encoded);
         String email = new String(bytesDec);
         System.out.println("mail"+email);
    	
    	User user= userRepository.getUserByEmail(email);
    	if(user!=null){
    			user.setConfirmed(true);
    			user.setEnabled(true);
    		 System.out.println(user.isConfirmed());
    		userRepository.save(user);
    		return new ResponseEntity<>(user, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    		
    	}
    }
    	
    
  
    
    
    
}
