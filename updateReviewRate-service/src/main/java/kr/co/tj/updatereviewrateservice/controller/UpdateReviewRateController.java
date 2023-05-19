package kr.co.tj.updatereviewrateservice.controller;

import java.util.Map;

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

	// 별점 평균 & 총 별점 입력 횟수
	@GetMapping("/rate/{sellerId}/stats")
	public ResponseEntity<?> getAverageRateAndCount(@PathVariable String sellerId) {
		try {
			Map<String, Object> result = updateReviewRateService.getAverageRateAndCount(sellerId);
			// 평균별점과 총 별점 입력 횟수 반환
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			// 예외 발생시 BAD_REQUEST 응답과 함께 에러 메시지 반환
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("별점 통계 계산에 실패했습니다.");
		}
	}

	// 테스트용
	@GetMapping("/health_check")
	public String status() {
		return "user service입니다" + env.getProperty("local.server.port") + ":" + env.getProperty("data.test");
	}
}
