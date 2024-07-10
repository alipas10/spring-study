package com.example.spring_mybatis.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.spring_mybatis.enitty.OrderEntity;
import com.example.spring_mybatis.projection.OrderProjection;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders", excerptProjection = OrderProjection.class)
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

	@RestResource(exported = true, path = "byID", rel = "findOrderbyID") // exported = false -> disable expose api
	Optional<OrderEntity> findById(@Param(value = "id") Long id);

}
