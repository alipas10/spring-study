package com.example.spring_mybatis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_mybatis.enitty.UserEntity;

@Repository
public interface UserRepositoy extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByName(String name);

	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByNameOrEmail(String name, String email);

}
