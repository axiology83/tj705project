package kr.co.tj.recordservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.recordservice.dto.BoardResponse;
import kr.co.tj.recordservice.dto.RecordDTO;
import kr.co.tj.recordservice.dto.RecordRequest;
import kr.co.tj.recordservice.dto.RecordResponse;
import kr.co.tj.recordservice.dto.ReviewResponse;
import kr.co.tj.recordservice.service.RecordService;

@RestController
@RequestMapping("/record-service")
public class RecordController {

	@Autowired
	private RecordService recordService;
	
	@PostMapping("/records") // record에 직접 post
	public ResponseEntity<?> createRecords(@RequestBody RecordRequest recordRequest){
		
		Long boardId = recordRequest.getBoardId();
		String buyer = recordRequest.getBuyer();
		
	    if (recordService.existsByBoardIdAndBuyer(boardId, buyer)) {  // boardId && buyer가 중복으로 있을 수 없게 하는 조건식 
	    	return ResponseEntity.status(HttpStatus.CREATED).body("중복");
	    } else {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(recordRequest);
			recordDTO=recordService.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			return ResponseEntity.status(HttpStatus.CREATED).body(recordResponse);
	    }
	}
	
	@PostMapping("/records/reviews") // reviewResponse를 받아와서 post
	public ResponseEntity<?> createRecords(@RequestBody ReviewResponse reviewResponse){
		
		Long boardId = reviewResponse.getBoardId();
		String buyer = reviewResponse.getBuyer();
		
		if(recordService.existsByBoardIdAndBuyer(boardId, buyer)) {  // boardId && buyer가 중복으로 있을 수 없게 하는 조건식 
			return ResponseEntity.status(HttpStatus.CREATED).body("중복");
		} else {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(reviewResponse);
			recordDTO=recordService.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			return ResponseEntity.status(HttpStatus.CREATED).body(recordResponse);
		}
	}
	
	@PostMapping("/records/boards") // boardResponse를 받아와서 post
	public ResponseEntity<?> createRecords(@RequestBody BoardResponse boardResponse){
		
		Long boardId = boardResponse.getBoardId();
		String buyer = boardResponse.getBuyer();
		
		if(recordService.existsByBoardIdAndBuyer(boardId, buyer)) {  // boardId && buyer가 중복으로 있을 수 없게 하는 조건식 
			return ResponseEntity.status(HttpStatus.CREATED).body("중복");
		} else {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(boardResponse);
			recordDTO=recordService.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			return ResponseEntity.status(HttpStatus.CREATED).body(recordResponse);
		}
	}
}
