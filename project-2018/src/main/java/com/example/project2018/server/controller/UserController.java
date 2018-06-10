package com.example.project2018.server.controller;

import java.awt.PageAttributes.MediaType;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.security.JwtTokenUtil;
import com.example.project2018.server.security.JwtUser;


@RestController
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

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
    	System.out.println("Ovde");
    	System.out.println("Ovde"+userDTO.getEmail());
    	/*User user=userRepository.findByEmail(userDTO.getEmail());
    	System.out.println("Ovde"+userDTO.getEmail());
    	if(user==null)
    	{
    		try
    		{
    			//User user1=userService.createUser(userDTO);
    			//emailService.sentMail(user1);
    		
    		} catch (Exception e) 
    		{
    			e.printStackTrace();
    			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    		}
    	}
    	
    }*/
    	return new ResponseEntity<User>(HttpStatus.OK);
    }
}
