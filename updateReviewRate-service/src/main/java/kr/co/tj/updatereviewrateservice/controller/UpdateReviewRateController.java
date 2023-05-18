package kr.co.tj.updatereviewrateservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.updatereviewrateservice.dto.RateDTO;
import kr.co.tj.updatereviewrateservice.dto.RateStatsDTO;
import kr.co.tj.updatereviewrateservice.service.UpdateReviewRateService;

@RestController
@RequestMapping("updateReviewRate-service")
public class UpdateReviewRateController {

	@Autowired
	private UpdateReviewRateService updateReviewRateService;
	
	@Autowired
	private Environment env;

	// 별점 입력
		@PostMapping("/rate/{sellerId}")
		public ResponseEntity<?> leaveReview(@PathVariable String sellerId, @RequestBody RateDTO rateDto) {
		    try {
		    	updateReviewRateService.leaveRate(sellerId, rateDto);
		        return ResponseEntity.status(HttpStatus.OK).body("별점이 업데이트되었습니다.");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("별점 업데이트에 실패했습니다.");
		    }
		}

		// 별점 통계
		@GetMapping("/rate/{sellerId}/stats")
		public ResponseEntity<?> getRateStats(@PathVariable String sellerId) {
			try {
				RateStatsDTO averageRate = updateReviewRateService.getRateStats(sellerId);
				return ResponseEntity.status(HttpStatus.OK).body("평균 별점: " + averageRate);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("별점 통계 계산에 실패했습니다.");
			}
		}
	
	// 테스트용
	@GetMapping("/health_check")
	public String status() {
		return "user service입니다" + env.getProperty("local.server.port")+":"+env.getProperty("data.test");
	}
}
