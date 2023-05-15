package kr.co.tj.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


import io.jsonwebtoken.Jwts;
//import lombok.Data;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.config> {

	private static final String SECRET_KEY ="mysecretekey";
	
	public AuthorizationFilter() {
		super(config.class);
	}
	
//	@Data
	public static class config{
//		private Integer num1;
//		private Integer num2;
	}

	@Override
	public GatewayFilter apply(config config) {
		
		return (exchange, chain) ->{
			ServerHttpRequest request = exchange.getRequest();
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "authorization 키가 없습니다.", HttpStatus.UNAUTHORIZED);
			}
			
			String bearerToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			//String token = bearerToken.split(" ")[1]; 아래와 같은뜻임
			String token = bearerToken.replace("Bearer ","");
			
			if(!isJwtValid(token)) {
				return onError(exchange, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}

	private boolean isJwtValid(String token) { //토큰 유효한지 검사해줌
		boolean isvalid = true;
		String subject = null;
		
		try {
			subject = Jwts.parser().setSigningKey(SECRET_KEY)
			.parseClaimsJws(token).getBody().getSubject();
			
		} catch (Exception e) {
			e.printStackTrace();
			isvalid = false;
		}
		
		if(subject==null || subject.isEmpty()) {
			isvalid = false;
		}
		
		return isvalid;
	}

	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		
		return response.setComplete();
	}

	
}
