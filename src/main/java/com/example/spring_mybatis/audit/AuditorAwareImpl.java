package com.example.spring_mybatis.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return Optional.of(authentication.getName()); // use curren log-in user if used Spring securiy
		// to populate to fields createBy and lastModifiedBy
	}

}
