package com.example.project2018.server.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.UserRepository;

public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User registerNewUser(User user) {
	
		return userRepository.save(user);
	}

	@Override
	public User getByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}

	@Override
	public User getByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	
}
