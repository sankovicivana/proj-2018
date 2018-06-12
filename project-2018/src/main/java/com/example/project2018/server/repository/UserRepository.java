package com.example.project2018.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2018.server.model.users.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User getUserByEmail(String email);
 
}
