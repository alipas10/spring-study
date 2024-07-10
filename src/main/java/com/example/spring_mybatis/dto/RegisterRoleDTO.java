package com.example.spring_mybatis.dto;

public class RegisterRoleDTO {

	private String nameRole;

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public RegisterRoleDTO(String nameRole) {
		super();
		this.nameRole = nameRole;
	}

	public RegisterRoleDTO() {
		super();
	}

}
