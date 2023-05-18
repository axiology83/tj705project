package kr.co.tj.updatereviewrateservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.updatereviewrateservice.dto.RateDTO;
import kr.co.tj.updatereviewrateservice.dto.RateStatsDTO;
import kr.co.tj.updatereviewrateservice.dto.UpdateReviewRateEntity;
import kr.co.tj.updatereviewrateservice.jpa.UpdateReviewRateRepository;

@Service
public class UpdateReviewRateService {
	
	@Autowired
	private UpdateReviewRateRepository updateReviewRateRepository;

	// 별점 입력
	public void leaveRate(String sellerId, RateDTO rateDto) {
		// 새로운 ReviewEntity 생성 후 저장
		UpdateReviewRateEntity newEntity = new UpdateReviewRateEntity();

		newEntity.setSellerId(sellerId);
		newEntity.setRate(rateDto.getRate());

		updateReviewRateRepository.save(newEntity);
	}

	// 별점 통계
	public RateStatsDTO getRateStats(String sellerId) {
		List<UpdateReviewRateEntity> reviews = updateReviewRateRepository.findAllBySellerId(sellerId);

		// 별점의 합계
		float sum = 0;

		// 별점의 최솟값
		float min = Float.MAX_VALUE;

		// 별점의 최댓값
		float max = Float.MIN_VALUE;

		for (UpdateReviewRateEntity review : reviews) {
			float rate = review.getRate();
			sum += rate;

			if (rate < min) {
				min = rate;
			}

			if (rate > max) {
				max = rate;
			}
		}

		// 평균 별점
		float avg = sum / reviews.size();

		RateStatsDTO stats = new RateStatsDTO();
		stats.setAverageRate(avg);
		stats.setMinRate(min);
		stats.setMaxRate(max);

		return stats;
	}
}