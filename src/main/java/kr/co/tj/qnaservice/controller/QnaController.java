package kr.co.tj.qnaservice.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.qnaservice.dto.QnaDTO;
import kr.co.tj.qnaservice.dto.QnaRequest;
import kr.co.tj.qnaservice.dto.QnaResponse;
import kr.co.tj.qnaservice.service.QnaService;


@RestController
@RequestMapping("/qna-service")
public class QnaController {
	
	@Autowired
	private QnaService qnaService;
	
	
	@GetMapping("/health_check")
	public String status() {
		
		return "qna-service 입니다";
	}
	
	// 게시물 입력
	@PostMapping("/qnas")   //  http://localhost:8000/qna-service/qnas
	public ResponseEntity<?> insert(@RequestBody QnaRequest qnaRequest){
		
		QnaDTO qnaDTO = QnaDTO.toQnaDTO(qnaRequest);
		qnaDTO = qnaService.insert(qnaDTO);
		
		QnaResponse qnaResponse = qnaDTO.toQnaResponse();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(qnaResponse);
	}
	

	// 게시물 수정
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody QnaResponse qnaResponse){
		
		QnaDTO qnaDTO = qnaService.update(qnaResponse);
		qnaResponse = qnaDTO.toQnaResponse();
		
		qnaDTO.setTitle(qnaResponse.getTitle());
		qnaDTO.setContent(qnaResponse.getContent());
		qnaDTO.setUpdateDate(qnaResponse.getUpdateDate());

		return ResponseEntity.status(HttpStatus.CREATED).body(qnaResponse);
	}
	
	
	// 게시물 삭제
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody QnaRequest qnaRequest) {
		
		try {
			qnaService.delete(qnaRequest.getId());
			
			return ResponseEntity.status(HttpStatus.OK).body("삭제 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
	}
	
	// 페이징
	@GetMapping("/page/{page}")
	public ResponseEntity<?> getpage(@PathVariable("page") Integer page) {
		
		page = page -1;
		
		Pageable pageable = PageRequest.of(page, 10);
		List<QnaResponse> list = qnaService.getPage(pageable);
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
		
	}
	
	// 게시물 목록 전체
	@GetMapping("/listall")
	public ResponseEntity<?> findList() {
		
		List<QnaDTO> list = qnaService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	
	// 게시물 자세히 보기
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		
		if(id == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 정보입니다");
		}
		
		QnaDTO dto = qnaService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	
	
	
	

	

}
