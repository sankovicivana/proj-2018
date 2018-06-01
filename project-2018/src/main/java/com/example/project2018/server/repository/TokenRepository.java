package com.example.project2018.server.repository;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2018.server.model.Token;
import com.example.project2018.server.model.users.User;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Token findByToken(String token);
	Token findByUser(User user);
	Stream<Token> findAllByExpiryDateLessThan(Date now);
}
