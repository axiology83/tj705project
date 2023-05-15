package kr.co.tj.reviewservice.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.reviewservice.dto.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

	List<ReviewEntity> findBySellerId(String sellerId);

}
