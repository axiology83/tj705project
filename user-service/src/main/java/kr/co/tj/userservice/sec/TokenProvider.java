package kr.co.tj.userservice.sec;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.tj.userservice.persistence.UserEntity;

@Component
public class TokenProvider {
	
	private static final String SECRET_KEY = "tj705team";
	
	
	public String create(UserEntity userEntity) {
		long now = System.currentTimeMillis();
		
		Date today = new Date(now);
		Date expireDate = new Date(now + 1000*60*60*24);
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.setSubject(userEntity.getUsername())
				.setIssuer("user-service")
				.setIssuedAt(today)
				.setExpiration(expireDate)
				.compact();
	}

}