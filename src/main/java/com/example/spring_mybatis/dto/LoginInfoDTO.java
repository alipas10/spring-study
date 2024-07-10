package com.example.spring_mybatis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginInfoDTO {
	@NotBlank
	@Size(min = 3, max = 50)
	private String userNameOrEmail;

	@NotBlank
	@Size(min = 3, max = 30)
	private String password;

	public String getUserNameOrEmail() {
		return userNameOrEmail;
	}

	public void setUserNameOrEmail(String userNameOrEmail) {
		this.userNameOrEmail = userNameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String passworld) {
		this.password = passworld;
	}

	public LoginInfoDTO(String userNameOrEmail, String passworld) {
		super();
		this.userNameOrEmail = userNameOrEmail;
		this.password = passworld;
	}

	public LoginInfoDTO() {
		super();
	}

}
