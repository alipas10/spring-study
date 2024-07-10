package com.example.spring_mybatis.projection;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.example.spring_mybatis.enitty.OrderEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "expDate","id","price"})
@Projection(name = "orderProjection", types = {OrderEntity.class} )
public interface OrderProjection {
	
	Long getPrice();
	Long getId();
	Date getExpDate();
	
	@Value("#{target.getPrice() + 1}")
	Long getPricePlus1();

}
