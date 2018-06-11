package com.example.project2018.server.security;

import com.example.project2018.server.dto.UserDTO;

public class JwtAuthenticationResponse {

	private UserDTO user;
	private String token;
	public JwtAuthenticationResponse() {
		super();
	}
	
	public JwtAuthenticationResponse(UserDTO user, String token) {
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
