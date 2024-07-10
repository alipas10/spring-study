package com.example.spring_mybatis.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring_mybatis.enitty.UserEntity;
import com.example.spring_mybatis.repository.UserRepositoy;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepositoy userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByNameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username));

		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());

		return new User(user.getName(), user.getPassword(), authorities);
	}
}
	