package com.example.project2018.server.service;

import java.util.Date;
import java.util.HashSet;

import javax.mail.UIDFolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project2018.server.dto.PasswordForgotDTO;
import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.model.users.RoleEnum;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.RoleRepository;
import com.example.project2018.server.repository.UserRepository;
import com.example.project2018.server.security.service.JwtUserDetailsService;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private final int MAX_ATTEMPTS = 3;
	private final long LOCKED_TIME = 600000; //10 minuta
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
		  user.setAccountNonLocked(true);
		  user.setConfirmed(false);
		  user.setEnabled(false);
		  user.setRoles(new HashSet<>());
		  user.getRoles().add(roleService.getByName(RoleEnum.ROLE_REGULAR.name()));
		  userRepository.save(user);
		  return user;
	  }

	@Override
	public boolean checkLockedExpired(String username) {
		User user = userRepository.findByUsername(username);
		int loginAttempts = user.getLoginAttempts();
		Date lastLogin = user.getLastLoginAttempt();
		Date now = new Date();
		if (!user.isAccountNonLocked()) {
			if (lastLogin.getTime() + LOCKED_TIME <= now.getTime()) {
				user.setAccountNonLocked(true);
				logger.info("Korisnik: {} vise nije zakljucan1. ", username);
				return true;
			} else {
				logger.info("Korisnik: {} je i dalje zakljucan. ", username);
				return false;
			}
		} else {
			logger.info("Korisnik: {} vise nije zakljucan2. ", username);
			user.setAccountNonLocked(true);
			user.setLoginAttempts(0);
			return true;
		}
		

	}

	@Override
	public int getLoginAttempts(String username) {
		User user = userRepository.findByUsername(username);
		return user.getLoginAttempts();
	}

	@Override
	public User lockUser(String username) {
		User user = userRepository.findByUsername(username);
		
		user.setAccountNonLocked(false);
		
		return userRepository.save(user);
	}

	@Override
	public User badLoginAttempt(String username) {
		User user = userRepository.findByUsername(username);
		logger.info("Korisnik: {} je uneo pogresnu sifru. ", username);
		
		int loginAttempts = user.getLoginAttempts();
		
		Date lastLogin = user.getLastLoginAttempt();
		
		
		
		if (user.isAccountNonLocked()) {
			if ( loginAttempts + 1 >= MAX_ATTEMPTS) {
				//zakljucaj korisnika
				user.setAccountNonLocked(false);
				user.setLoginAttempts(loginAttempts + 1); 
				user.setLastLoginAttempt(new Date());
				Date date = user.getLastLoginAttempt();
				Long time = date.getTime() + LOCKED_TIME;
				date = new Date(time);
				logger.info("Korisnik: {} se zakljucava jer je uneo pogresnu sifru {} puta. ", username, user.getLoginAttempts());
				logger.info("Korisnik: {} ce biti otkljucan {}. ", username, date);
				return userRepository.save(user);
			} else {
				user.setLoginAttempts(loginAttempts + 1); 
				user.setLastLoginAttempt(new Date());
				logger.info("Korisnik: {} je uneo pogresnu sifru {} puta. ", username, loginAttempts + 1);
				return userRepository.save(user);
			}

		}else
			return user;
		
	}

	@Override
	public User succesLogIn(String username) {
		User user = userRepository.findByUsername(username);
		user.setLoginAttempts(0);
		user.setAccountNonLocked(true);
		return userRepository.save(user);
	}

	@Override
	public User updatePassword(String password,String username) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username);
		
		 user.setPassword(password);
		 userRepository.save(user);
		return user;
	}

	

	
	
}
