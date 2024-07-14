package com.example.spring_mybatis.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> map = new HashMap<>();
		e.getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String massage = error.getDefaultMessage();
			map.put(fieldName, massage);
		});
		return ResponseEntity.badRequest().body(map);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
		Map<String, String> map = new HashMap<>();
		e.getConstraintViolations().forEach(error -> {
			String propertyName = error.getPropertyPath().toString();
			String message = error.getMessage();
			map.put(propertyName, message);
		});
		return ResponseEntity.badRequest().body(map);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return new ResponseEntity<>("Type request not correct!", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseBody
	public ResponseEntity<?> handleBadCredentialsException (BadCredentialsException e){
		return new ResponseEntity<>("User name or password not correct !", HttpStatus.UNAUTHORIZED);
	}

}
