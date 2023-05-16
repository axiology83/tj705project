package kr.co.tj.userservice.feign;

//import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;

//import kr.co.tj.reviewservice.dto.ReviewResponse;

@FeignClient(name = "qna-service")
public interface QnAFeign {
	
//	@GetMapping("/qna-service/reviews/user/{username}")
//	public List<QnAResponse> getQnAsByUsername(@PathVariable() String username);
	
}
