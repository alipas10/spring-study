package com.example.spring_mybatis.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.spring_mybatis.enitty.OrderEntity;

public class OrderValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return OrderEntity.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrderEntity order = (OrderEntity) target;

		if (order.getPrice() == null) {
			errors.rejectValue("price", "price.isNull");
		}

		if (order.getPrice().toString().matches("^[^1-9]+")) {
			errors.rejectValue("price", "price.notNumber");
		}
	}

}
