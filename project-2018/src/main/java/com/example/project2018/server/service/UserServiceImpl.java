package com.example.project2018.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	@Override
	public User createUser(UserDTO userDTO){
		  User user=new User();
		  user.setFirstname(userDTO.getFirstname());
		  user.setLastname(userDTO.getLastname());
		  user.setUsername(userDTO.getUsername());
		  user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		  user.setEmail(userDTO.getEmail());
		
		  
		  userRepository.save(user);
		  return user;
	  }
	
}
