package kr.co.tj.updatereviewrateservice.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.updatereviewrateservice.dto.UpdateReviewRateEntity;


public interface UpdateReviewRateRepository extends JpaRepository<UpdateReviewRateEntity, Long> {

	List<UpdateReviewRateEntity> findAllBySellerId(String sellerId);

}
