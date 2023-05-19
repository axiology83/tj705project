package kr.co.tj.updatereviewrateservice.service;

import java.util.Map;

import kr.co.tj.updatereviewrateservice.dto.RateDTO;

public interface UpdateReviewRateService {
	
	void leaveRate(String sellerId, RateDTO rateDTO);
	void updateRate(Long rid, RateDTO rateDTO);
	void deleteRate(Long rid, RateDTO rateDTO);
	Map<String, Object> getAverageRateAndCount(String sellerId);

}
