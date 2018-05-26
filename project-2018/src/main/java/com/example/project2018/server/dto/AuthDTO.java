package com.example.project2018.server.dto;

public class AuthDTO {

	private UserDTO user;
	private String token;
	public AuthDTO() {
		super();
	}
	
	public AuthDTO(UserDTO user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
