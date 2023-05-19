package kr.co.tj.updatereviewrateservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.updatereviewrateservice.dto.RateDTO;
import kr.co.tj.updatereviewrateservice.dto.UpdateReviewRateEntity;
import kr.co.tj.updatereviewrateservice.jpa.UpdateReviewRateRepository;

@Service
public class UpdateReviewRateService {
	
	@Autowired
	private UpdateReviewRateRepository updateReviewRateRepository;
	
	// 별점 입력
	public void leaveRate(String sellerId, RateDTO rateDto) {
	    // sellerId를 기준으로 RateEntity를 검색
	    Optional<UpdateReviewRateEntity> entity = updateReviewRateRepository.findBySellerId(sellerId);
	    
	    if(entity.isPresent()) {
	        // sellerId에 별점이 이미 존재할 경우, rate와 count를 업데이트
	        UpdateReviewRateEntity rateEntity = entity.get();
	        // 총 별점에 새로운 별점을 더함
	        rateEntity.setRate(rateEntity.getRate() + rateDto.getRate());
	        // 별점 입력시 count 1씩 추가
	        rateEntity.setCount(rateEntity.getCount()+1);
	        updateReviewRateRepository.save(rateEntity);
	    } else {
	        // 존재하지 않는 경우, 새로운 Entity 생성 후 저장
	        UpdateReviewRateEntity newEntity = new UpdateReviewRateEntity();
	        newEntity.setSellerId(sellerId);
	        newEntity.setRate(rateDto.getRate()); // 첫 별점을 저장
	        newEntity.setCount(1); // count의 초기값을 1로 설정
	        
	        updateReviewRateRepository.save(newEntity);
	    }
	}
	
	// 평균 별점
	public Map<String, Object> getAverageRateAndCount(String sellerId) {
		// sellerId를 기준으로 RateEntity를 검색
	    Optional<UpdateReviewRateEntity> entity = updateReviewRateRepository.findBySellerId(sellerId);
	    
	    if(entity.isPresent()) {
	        UpdateReviewRateEntity rateEntity = entity.get();
	        Map<String, Object> result = new HashMap<>();
	        // 별점 평균값 계산 (총 별점 합계 / 총 count)
	        result.put("averageRate", (float) rateEntity.getRate() / rateEntity.getCount());
	        // 총 count 반환
	        result.put("count", rateEntity.getCount());
	        return result;
	    } else {
	        // 별점 정보가 없는 경우, 예외처리
	        throw new RuntimeException("아직" + sellerId + "의 별점정보가 없슴니다. ");
	    }
	}
}