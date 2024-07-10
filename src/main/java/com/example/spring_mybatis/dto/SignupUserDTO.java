package com.example.spring_mybatis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupUserDTO {

	@NotBlank
	@Size(min = 3, max = 50)
	private String userName;

	@NotBlank
	@Size(min = 3, max = 30)
	private String password;

	@Email(regexp = "^[a-zA-Z0-9]+@{1}.+[\\.com]{1}$")
	@NotBlank
	@Size(min = 6, max = 50)
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SignupUserDTO(String userName, String password, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public SignupUserDTO() {
		super();
	}

}
