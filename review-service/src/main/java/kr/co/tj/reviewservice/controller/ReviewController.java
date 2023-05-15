package kr.co.tj.reviewservice.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.reviewservice.dto.ReviewDTO;
import kr.co.tj.reviewservice.dto.ReviewRequest;
import kr.co.tj.reviewservice.dto.ReviewResponse;
import kr.co.tj.reviewservice.service.ReviewService;

@RestController
@RequestMapping("review-service")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	// 페이징 구현
	@GetMapping("/page/{page}")
	public ResponseEntity<?> getpage(@PathVariable("page") Integer page){
		page = page - 1;
		Pageable pageable = PageRequest.of(page, 10);
		List<ReviewResponse> list = reviewService.getPage(pageable);
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	

	// 판매자 리뷰 자세히 보기
	@GetMapping("/review/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {

		try {
			ReviewResponse reviewResponse = reviewService.findById(id);
			return ResponseEntity.status(HttpStatus.OK).body(reviewResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰게시물 정보가없어 불러오지 못했습니다");
		}
	}

	// 판매자이름 검색해서 리뷰 모두 보기
	@GetMapping("/review/sellerFindReview")
	public ResponseEntity<?> findBySeller(@RequestParam(name = "keyword") String keyword) {

		if (keyword == null || keyword == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("판매자 정보가 없습니다");
		}

		List<ReviewResponse> list = reviewService.findBySeller(keyword);

		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰가없습니다.2");
		}

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 리뷰 모두 보기
	@GetMapping("/review")
	public ResponseEntity<?> findAll() {

		List<ReviewResponse> list = reviewService.findAll();

		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰가없습니다.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 리뷰 입력
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ReviewRequest reviewRequest) {

		if (reviewRequest.getBuyerName() == null || reviewRequest.getSellerId() == null
				|| reviewRequest.getTitle() == null || reviewRequest.getContent() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력된 값이 모두 채워지지 않았습니다.");
		}

		ReviewDTO dto = ReviewDTO.toReviewDTOreq(reviewRequest);

		dto = reviewService.createReview(dto);

		ReviewResponse reviewResponse = dto.toReviewResponse();

		return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);
	}

	// 리뷰 수정
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ReviewRequest reviewRequest) {

		if (reviewRequest.getId() == null || reviewRequest.getId().equals("")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 정보가 없습니다.");
		}
		
		if (reviewRequest.getTitle() == null || reviewRequest.getTitle() == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 제목을 확인해주세요");
		}
		
		if (reviewRequest.getContent() == null || reviewRequest.getContent() == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 정보가 없습니다.");
		}

		ReviewDTO dto = ReviewDTO.toReviewDTOreq(reviewRequest);

		dto = reviewService.updateReview(dto);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰에 대한 글이없습니다.");
		}

		ReviewResponse reviewResponse = dto.toReviewResponse();

		return ResponseEntity.status(HttpStatus.OK).body(reviewResponse);
	}

	// 리뷰 삭제

	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody ReviewRequest reviewRequest) {

		try {
			reviewService.delete(reviewRequest.getId());

			return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시글이 없습니다.");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// 리뷰 많이 입력
	@PostMapping("/testinsert")
	public void testinsert() {
		
		Random rand = new Random();
		
		for(int i = 1; i<500; i++) {
			String sti = String.format("%04d", i);
			int year = rand.nextInt(3) + 2021;
			int month = rand.nextInt(12) + 1;
			int day = rand.nextInt(28) + 1;
			Calendar cal = Calendar.getInstance();
			cal.set(year, month-1, day);
			Date date = cal.getTime();
			
			ReviewDTO dto = new ReviewDTO(null, "seller"+sti, "buyer"+sti, "이글의"+sti+"번째 리뷰입니다", "리뷰 내용"+ sti, 0, 3.0f, date, date);
			
			reviewService.testinsert(dto);
		}
	}
}
