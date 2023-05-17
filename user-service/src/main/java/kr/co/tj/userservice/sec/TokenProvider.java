package kr.co.tj.userservice.sec;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.tj.userservice.persistence.UserEntity;

@Component
public class TokenProvider {
	
	//private static final String SECRET_KEY = "tj705team";
	private Environment env;
	
	@Autowired
	public TokenProvider(Environment env) {
		super();
		this.env = env;
	}

	public String create(UserEntity userEntity) {
		long now = System.currentTimeMillis();
		
		Date today = new Date(now);
		Date expireDate = new Date(now + 1000*60*60*24);
		String str = env.getProperty("data.SECRET_KEY");
		String encodedStr = Base64.getEncoder().encodeToString(str.getBytes());
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, encodedStr)
				.setSubject(userEntity.getUsername())
				.setIssuer("user-service")
				.setIssuedAt(today)
				.setExpiration(expireDate)
				.compact();
	}

}