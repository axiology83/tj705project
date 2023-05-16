package kr.co.tj.userservice.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.tj.userservice.dto.UserEntity;

@Component
public class TokenProvider {
	
	private static final String SECRETE_KEY = "aaaaaaaaaa";
	
	public String create(UserEntity userEntity) {
		
		long now = System.currentTimeMillis();
		
		Date today = new Date(now);
		Date exireDate = new Date(now + 1000 * 1 * 60 * 60 * 24);
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRETE_KEY)
				.setSubject(userEntity.getUsername())
				.setIssuer("user-service")
				.setIssuedAt(today)
				.setExpiration(exireDate)
				.compact();
	}

}
