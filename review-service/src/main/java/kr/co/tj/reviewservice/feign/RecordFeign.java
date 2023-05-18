package kr.co.tj.reviewservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.tj.reviewservice.dto.RecordResponse;
import kr.co.tj.reviewservice.dto.ReviewResponse;


@FeignClient(name = "record-service")
public interface RecordFeign {

	
	@GetMapping("record-service/reviews")
	public RecordResponse createRecords(@RequestBody ReviewResponse reviewResponse);
}
