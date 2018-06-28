package com.example.project2018.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2018.server.model.users.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);

}
