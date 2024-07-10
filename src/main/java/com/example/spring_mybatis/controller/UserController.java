package com.example.spring_mybatis.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_mybatis.dto.LoginInfoDTO;
import com.example.spring_mybatis.dto.SignupUserDTO;
import com.example.spring_mybatis.enitty.RoleEntity;
import com.example.spring_mybatis.enitty.UserEntity;
import com.example.spring_mybatis.repository.RoleRepository;
import com.example.spring_mybatis.repository.UserRepositoy;
import com.example.spring_mybatis.utility.JwtUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/admin")
public class UserController {

	@Autowired
	UserRepositoy userRepo;
	
	@Autowired
	RoleRepository roleRepo;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder pwEncoder;
	
	@Autowired
	JwtUtility jwtUtility;

	@PostMapping(value = "/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupUserDTO signupUserDTO) {
		if (userRepo.findByNameOrEmail(signupUserDTO.getUserName(), signupUserDTO.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("The user name or email already taken! ");
		}

		UserEntity userSave = new UserEntity();
		userSave.setEmail(signupUserDTO.getEmail());
		userSave.setName(signupUserDTO.getUserName());
		Optional<RoleEntity> roleE = roleRepo.findByRole("ROLE_USER");
		if(roleE.isEmpty())
			return ResponseEntity.internalServerError().body("There is error in find Role");
		Set<RoleEntity> set = new HashSet<>();
		set.add(roleE.get());
		userSave.setRoles(set);
		userSave.setPassword(pwEncoder.encode(signupUserDTO.getPassword()));

		UserEntity userResponse = userRepo.save(userSave);
		if (Optional.of(userResponse).isEmpty())
			return ResponseEntity.badRequest().body("There is an error when save User!");
		return ResponseEntity.ok(userResponse);
	}

	@PostMapping(value = "/signin")
	public ResponseEntity<?> signIn(@Valid @RequestBody LoginInfoDTO login) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUserNameOrEmail(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtUtility.generateToken(authentication);
		return ResponseEntity.ok(token);
	}
}
