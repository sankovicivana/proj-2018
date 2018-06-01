package com.example.project2018.server.service;

import org.springframework.stereotype.Service;

import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.model.users.User;
@Service
public interface UserService {
	
	User registerNewUser(User usert);
	User getByEmail(String email);
	User getByUsername(String username);
}
