package com.example.spring_mybatis.enitty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class EmployeeEntity {
	
//	@NotNull
//	@Pattern(regexp = "^[0-9]+")
	private Integer id;
	
	@NotNull
	private String name;
	
	@NotNull
	@Email
	private String email;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public EmployeeEntity(Integer id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}
	public EmployeeEntity() {
		super();
	}
	
	
}
