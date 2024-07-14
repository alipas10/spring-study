package com.example.spring_mybatis.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.example.spring_mybatis.config.filter.JwtAuthFilter;
import com.example.spring_mybatis.utility.CustomJwtAuthenticationConverter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

	private static final String GROUPS = "groups";
	private static final String REALM_ACCESS_CLAIM = "realm_access";
	private static final String ROLES_CLAIM = "roles";

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(sessionRegistry());
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

//	@formatter:off

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				// .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
				.requestMatchers("/admin/signin")
				.permitAll()
				.requestMatchers("/admin/**")
				.hasRole("user")
				.anyRequest()
				.authenticated())
			.headers(headers -> headers.disable());
		
//		config crsf
//        .csrf(csrf -> csrf
//                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));
		http.csrf(csrf -> csrf.disable());
		
//		config header
//			http basic authentication
		http.httpBasic(Customizer.withDefaults());
			
//			Oauth2 config authorization server
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
			jwt.jwtAuthenticationConverter(new CustomJwtAuthenticationConverter())));
		
//		Oauth2 Client
		http.oauth2Login(Customizer.withDefaults());
			
		
//		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

//	@formatter:on

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();

	}

//	@Bean
//	public JwtAuthenticationConverter jwtAuthenticationConverter() {
//		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//
//		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//		return jwtAuthenticationConverter;
//	}

	@Bean
	public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
		return authorities -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
			var authority = authorities.iterator().next();
			boolean isOidc = authority instanceof OidcUserAuthority;

			if (isOidc) {
				var oidcUserAuthority = (OidcUserAuthority) authority;
				var userInfo = oidcUserAuthority.getUserInfo();

				// Tokens can be configured to return roles under
				// Groups or REALM ACCESS hence have to check both
				if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
					var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
					var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
					mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
				} else if (userInfo.hasClaim(GROUPS)) {
					Collection<String> roles = userInfo.getClaim(GROUPS);
					mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
				}
			} else {
				var oauth2UserAuthority = (OAuth2UserAuthority) authority;
				Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

				if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
					Map<String, Object> realmAccess = (Map<String, Object>) userAttributes.get(REALM_ACCESS_CLAIM);
					Collection<String> roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
					mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));

				}
			}
			return mappedAuthorities;
		};
	}

	Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
	}

//
//	@Bean
//	public UserDetailsService usDetails() {
//		UserDetails userD = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//				.build();
//		UserDetails userD1 = User.builder().username("user").password(passwordEncoder().encode("user")).roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(userD,userD1);
//	}
}
