package kr.co.tj.recordservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.recordservice.dto.BoardResponse;
import kr.co.tj.recordservice.dto.RecordDTO;
import kr.co.tj.recordservice.dto.RecordRequest;
import kr.co.tj.recordservice.dto.RecordResponse;
import kr.co.tj.recordservice.dto.ReviewResponse;
import kr.co.tj.recordservice.service.RecordServiceImpl;

@RestController
@RequestMapping("/record-service")
public class RecordController {

	@Autowired
	private RecordServiceImpl recordServiceImpl;
	
	@PostMapping("/records") // record에 직접 post
	public ResponseEntity<?> createRecords(@RequestBody RecordRequest recordRequest){
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(recordRequest);
			recordDTO=recordServiceImpl.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			map.put("result", recordResponse);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@PostMapping("/boards") // boardResponse를 받아와서 post // postman 입력 시 BoardResponse의 변수를 사용해야한다.
	public ResponseEntity<?> createRecords(@RequestBody BoardResponse boardResponse){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(boardResponse);
			recordDTO=recordServiceImpl.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			map.put("result", recordResponse);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 board를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	

	@PostMapping("/reviews") // reviewResponse를 받아와서 post // postman 입력 시 ReviewResponse의 변수를 사용해야한다.
	public ResponseEntity<?> createRecords(@RequestBody ReviewResponse reviewResponse){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(reviewResponse);
			recordDTO=recordServiceImpl.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			map.put("result", recordResponse);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 review를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	@PutMapping("/records") // record에 put한 것을 create
	public ResponseEntity<?> createUpdateRecords(@RequestBody RecordRequest recordRequest){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(recordRequest);
			recordDTO=recordServiceImpl.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			map.put("result", recordResponse);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	
	@PutMapping("/boards") // board에 put한 것을 create
	public ResponseEntity<?> createUpdateRecords(@RequestBody BoardResponse boardResponse){

		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(boardResponse);
			recordDTO=recordServiceImpl.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			map.put("result", recordResponse);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 board를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@PutMapping("/reviews") // review에 put한 것을 create
	public ResponseEntity<?> createUpdateRecords(@RequestBody ReviewResponse reviewResponse){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(reviewResponse);
			recordDTO=recordServiceImpl.createRecord(recordDTO);
			RecordResponse recordResponse=recordDTO.toRecordResponse();
			
			map.put("result", recordResponse);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 review를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
}
