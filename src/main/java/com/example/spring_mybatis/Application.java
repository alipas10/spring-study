package com.example.spring_mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.spring_mybatis.audit.AuditorAwareImpl;

@SpringBootApplication
@MapperScan("com.example.spring_mybatis.mapper") // enable scan file mapper of mybatis
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // enable auditing specify bean of class implementation of
														// interface AuditorAware
public class Application {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
