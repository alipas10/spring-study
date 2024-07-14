package com.example.spring_mybatis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_mybatis.dto.RegisterRoleDTO;
import com.example.spring_mybatis.enitty.RoleEntity;
import com.example.spring_mybatis.repository.RoleRepository;


@RestController
@RequestMapping(value = "/admin/role")
public class RoleController {

	@Autowired
	RoleRepository roleRepo;

	@PostMapping(path = "/addRole")
	public ResponseEntity<?> createMovie(@RequestBody RegisterRoleDTO role) {
		if(!role.getNameRole().startsWith("ROLE_")) {
			role.setNameRole("ROLE_".concat(role.getNameRole()));
		}
		Optional<RoleEntity> resultDTO = Optional.of(roleRepo.save(new RoleEntity(role.getNameRole())));
		if (resultDTO.isEmpty())
			return ResponseEntity.badRequest().body("There is error when add role!");
		return ResponseEntity.ok(resultDTO.get());
	}

	@GetMapping(path = "/listRoles")
	public ResponseEntity<?> listAllRoles(){
		List<RoleEntity> roles = roleRepo.findAll();
		if(Optional.of(roles).isEmpty())
			return ResponseEntity.badRequest().body("There is error when get list Role");
		return ResponseEntity.ok().body(roles);
	}
}
