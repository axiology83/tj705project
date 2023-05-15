package kr.co.tj.recordservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.tj.recordservice.dto.ReviewResponse;

@FeignClient(name = "review-service")
public interface ReviewFeign {
	@GetMapping("/review-service/reviews/user/{username}")   
	public List<ReviewResponse> getReviewsByUsername(@PathVariable("username") String username);
}
