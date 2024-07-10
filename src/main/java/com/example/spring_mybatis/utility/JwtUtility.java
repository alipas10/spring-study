package com.example.spring_mybatis.utility;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtility {
	
    private static final Logger logger = LoggerFactory.getLogger(JwtUtility.class);

	
	@Value(value = "${app.jwt-secret}")
	private String jwtSecret;

	@Value(value = "${app.jwt-expiration-milliseconds}")
	private Long jwtExpiration;
	
	public String generateToken(Authentication authentication) {
		String userName = authentication.getName();
		
		Date currenDate = new Date();
		
		Date expireDate = new Date( currenDate.getTime() + jwtExpiration);
		
		String token = Jwts.builder().setSubject(userName)
									.setIssuedAt(currenDate)
									.setExpiration(expireDate)
									.signWith(key())
									.compact();
		return token;
									
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUserName(String token) {
		return Jwts.parserBuilder()
					.setSigningKey(key())
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
	}
	
	public Boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parse(token);
			return true;
		}catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
		
	}
}
