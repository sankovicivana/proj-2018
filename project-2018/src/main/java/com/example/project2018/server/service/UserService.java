package com.example.project2018.server.service;



import org.springframework.stereotype.Service;

import com.example.project2018.server.dto.PasswordForgotDTO;
import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.model.users.User;


public interface UserService {
	
	User registerNewUser(User usert);
	User getByEmail(String email);
	User getByUsername(String username);
	User createUser(UserDTO userDTO);
	boolean checkLockedExpired(String username);
	int getLoginAttempts(String username);
	User lockUser(String username);
	User badLoginAttempt(String username);
	User succesLogIn(String username);
	

}
