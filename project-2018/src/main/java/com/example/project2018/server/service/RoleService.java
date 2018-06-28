package com.example.project2018.server.service;

import java.util.List;

import com.example.project2018.server.model.users.Role;

public interface RoleService {
	
	public List<Role> getRoles();
	public Role getRole(Long id);
	public Role addRole(Role role);
	public Role deleteRole(Role role);
	public Role deleteRole(Long id);
	public Role getByName(String name);
}
