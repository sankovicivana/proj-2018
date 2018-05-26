package com.example.project2018.server.dto;

import java.util.List;

public class UserDTO {

	private String username;
	private List<String> roles;
	
	
	public UserDTO(String username, List<String> roles) {
		super();
		this.username = username;
		this.roles = roles;
	}
	public UserDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
