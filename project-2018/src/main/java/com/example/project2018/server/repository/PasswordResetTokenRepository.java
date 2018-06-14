package com.example.project2018.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.project2018.server.model.Token;

public interface PasswordResetTokenRepository  extends JpaRepository<Token, Long>{
		
	Token findByToken(String token);
	
}
