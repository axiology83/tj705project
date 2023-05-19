package kr.co.tj.updatereviewrateservice.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.tj.updatereviewrateservice.dto.UpdateReviewRateEntity;

@Repository
public interface UpdateReviewRateRepository extends JpaRepository<UpdateReviewRateEntity, Long> {

	Optional<UpdateReviewRateEntity> findByRid(Long rid);

	List<UpdateReviewRateEntity> findBySellerId(String sellerId);

}
