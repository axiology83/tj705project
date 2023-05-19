package kr.co.tj.updatereviewrateservice.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.updatereviewrateservice.dto.UpdateReviewRateEntity;


public interface UpdateReviewRateRepository extends JpaRepository<UpdateReviewRateEntity, Long> {

	Optional<UpdateReviewRateEntity> findBySellerId(String sellerId);

}
