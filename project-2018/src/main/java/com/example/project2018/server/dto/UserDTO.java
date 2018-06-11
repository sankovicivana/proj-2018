package com.example.project2018.server.dto;

import java.util.List;

public class UserDTO {

	private String username;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private List<String> roles;

	
	public UserDTO(String username, List<String> roles) {
		super();
		this.username = username;
		this.roles = roles;
	}
	public UserDTO() {
		super();
	}

	public UserDTO(String username, String email, String password, String firstname, String lastname) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
