package com.example.project2018.server.dto;

import javax.validation.constraints.NotBlank;

import com.example.project2018.server.validation.ValidPassword;


public class PasswordRequest {

	@NotBlank
	private String tokenId;

	@NotBlank
	@ValidPassword
	private String newPassword;

	@NotBlank
	private String confirmPassword;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
