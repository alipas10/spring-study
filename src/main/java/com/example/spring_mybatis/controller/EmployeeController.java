package com.example.spring_mybatis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_mybatis.enitty.EmployeeEntity;
import com.example.spring_mybatis.mapper.EmployeeMapper;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/employee")
@Validated
public class EmployeeController {

	@Autowired
	private EmployeeMapper empR;

	@PostMapping(path = "/insertOne")
	public ResponseEntity<Integer> createMovie(@RequestBody EmployeeEntity empDTO, BindingResult bind) {
		if (bind.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		Integer result = empR.insert(empDTO);
		if (result == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.ok(result);
	}

	@GetMapping(path = "/findById")
	public ResponseEntity<EmployeeEntity> findById(@RequestParam(value = "id", required = true) @Min(1) Integer Id) {
		Optional<EmployeeEntity> empDTO = empR.findByID(Id);
		return ResponseEntity.of(empDTO);
	}

	@GetMapping(path = "/findByName")
	public ResponseEntity<List<EmployeeEntity>> findByName(@RequestParam(value = "name", required = true) String Name) {
		return ResponseEntity.of(Optional.ofNullable(empR.findByName(Name)));
	}

	@PutMapping(path = "/updateEmployee")
	public ResponseEntity<EmployeeEntity> updateEmployee(@RequestBody EmployeeEntity empDTO) {
		empR.updateEmployee(empDTO);
		return ResponseEntity.of(empR.findByID(empDTO.getId()));
	}

//	
//	@DeleteMapping(path = "/deleteEmployee")
//	public ResponseEntity<EmployeeDTO> deleteEmployee( @RequestBody EmployeeDTO empDTO, BindingResult bindResult) {
//		if(bindResult.hasErrors()) {
//			return ResponseEntity.badRequest().build();
//		}
//			
//		empR.deleteEployee(empDTO);
//		EmployeeDTO resultDTO = empR.findByID(empDTO.getId());
//		if(resultDTO == null ) return ResponseEntity.status(HttpStatus.OK).build(); 
//		return ResponseEntity.badRequest().body(resultDTO);
//	}

}
