package kr.co.tj.reviewservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.tj.reviewservice.dto.ReviewDTO;
import kr.co.tj.reviewservice.dto.ReviewEntity;
import kr.co.tj.reviewservice.dto.ReviewResponse;
import kr.co.tj.reviewservice.jpa.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	// 리뷰 수정
	public ReviewDTO updateReview(ReviewDTO dto) {
		
		
		// findById로 입력받은 dto id값으로 entity에 저장된 값을 불러옴
		Optional<ReviewEntity> optinal = reviewRepository.findById(dto.getId());
		if(optinal.isEmpty()) {
			return null;
		}
		// 리뷰의 판매자, 구매자 이름, createDate, 조회수 가져옴
		ReviewEntity originEntity = optinal.get();
		dto.setSellerId(originEntity.getSellerId());
		dto.setBuyerName(originEntity.getBuyerName());
		dto.setCreateDate(originEntity.getCreateDate());
		dto.setCount(originEntity.getCount());

		// 날짜 설정 (updateDate)
		dto = getDate(dto);
		ReviewEntity entity = dto.toReviewEntity();
		
		entity = reviewRepository.save(entity);
		
		return dto.toReviewDTO(entity);
	}

	// 리뷰 작성
	public ReviewDTO createReview(ReviewDTO dto) {
		
		dto = getDate(dto);
		dto.setCount(0);	//초기 조회수설정
		
		ReviewEntity entity = dto.toReviewEntity();
		
		entity = reviewRepository.save(entity);
		
		return dto.toReviewDTO(entity);
	}
	
	// 날짜 생성
	private ReviewDTO getDate(ReviewDTO dto) {
		Date date = new Date();
		
		if(dto.getCreateDate() == null) {
			dto.setCreateDate(date);
		}
		
		dto.setUpdateDate(date);
		return dto;
	}
	
	// 리뷰 전체글 보기
	public List<ReviewResponse> findAll() {
		List<ReviewEntity> entity = reviewRepository.findAll();
		List<ReviewResponse> reviewResponse = new ArrayList<>();
		
		entity.forEach(x -> {
			ReviewDTO dto = new ReviewDTO();
			ReviewResponse res = dto.toReviewFindResponse(x);
			reviewResponse.add(res);
			
		});
		return reviewResponse;
	}
	
	// 판매자 리뷰 목록보기
	public List<ReviewResponse> findBySeller(String sellerId) {

		List<ReviewEntity> entity = reviewRepository.findBySellerId(sellerId);
		List<ReviewResponse> reviewResponse = new ArrayList<>();
		
		entity.forEach(x -> {
			ReviewDTO dto = new ReviewDTO();
			ReviewResponse res = dto.toReviewFindResponse(x);
			reviewResponse.add(res);
		});
		return reviewResponse;
	}

	// 판매자 리뷰 자세히 보기
	public ReviewResponse findById(Long id) {
		Optional<ReviewEntity> optional = reviewRepository.findById(id);
		
		ReviewEntity entity = optional.get();
		entity.setCount(entity.getCount()+1);
		reviewRepository.save(entity);
		
		ReviewDTO dto = new ReviewDTO();
		ReviewResponse reviewResponse = dto.toReviewFindResponse(entity);
		return reviewResponse;
	}

	public void delete(Long id) {
		reviewRepository.deleteById(id);
		
	}

	// 페이징 구현
	public List<ReviewResponse> getPage(Pageable pageable) {
		Page<ReviewEntity> entity = reviewRepository.findAll(pageable);
		List<ReviewResponse> reviewResponse = new ArrayList<>();
		
		entity.forEach(x -> {
			ReviewDTO dto = new ReviewDTO();
			ReviewResponse res = dto.toReviewFindResponse(x);
			reviewResponse.add(res);
		});
		return reviewResponse;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void testinsert(ReviewDTO dto) {

		ReviewEntity entity = dto.toReviewEntity();
		
		reviewRepository.save(entity);
		
	}

	
	
}
