package com.example.project2018.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project2018.server.model.users.Role;
import com.example.project2018.server.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role getRole(Long id) {
		Role role = roleRepository.getOne(id);
		if (role == null) {
			return null;
		} else {
			return role;
		}
		
	}

	@Override
	public Role addRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role deleteRole(Role role) {
		Role retVal = roleRepository.getOne(role.getId());
		if (retVal == null) {
			return null;
		} else {
			roleRepository.delete(retVal);
			return retVal;
		}
		
	}

	@Override
	public Role deleteRole(Long id) {
		Role retVal = roleRepository.getOne(id);
		if (retVal == null) {
			return null;
		} else {
			roleRepository.delete(retVal);
			return retVal;
		}
	}

	@Override
	public Role getByName(String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			return null;
		}else {
		return role;
		}
	}
	
	
	
}
