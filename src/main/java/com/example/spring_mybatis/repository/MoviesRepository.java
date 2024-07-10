package com.example.spring_mybatis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_mybatis.enitty.MovieEntity;

@Repository
public interface MoviesRepository extends JpaRepository<MovieEntity, Long> {
}
