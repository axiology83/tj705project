package kr.co.tj.recordservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.recordservice.dto.BoardResponse;
import kr.co.tj.recordservice.dto.RecordDTO;
import kr.co.tj.recordservice.dto.RecordRequest;
import kr.co.tj.recordservice.dto.ReviewResponse;
import kr.co.tj.recordservice.service.RecordService;


@RestController
@RequestMapping("/record-service")
public class RecordController {
	
	@Autowired
	private RecordService recordService;
	
	// 레코드 전체 get
	@GetMapping("/records") 
	public ResponseEntity<?> findAll(){
		Map<String, Object> map=new HashMap<>();
		List<RecordDTO> list = recordService.findAll();

		if (list.isEmpty()) {
			map.put("result","record의 list가 존재하지 않습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
		else {
			try {
				map.put("result", list);
				return ResponseEntity.ok().body(map);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result","record의 list가 존재하지 않습니다.");
				
				return ResponseEntity.badRequest().body(map);
			}
		}
	}
	
	// 해당하는 id를 가진 레코드만 get
	@GetMapping("/records/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO dto=recordService.findById(id);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result","record가 존재하지 않습니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
	}
	
	// record에 직접 post
	@PostMapping("/records") 
	public ResponseEntity<?> createRecords(@RequestBody RecordRequest recordRequest){
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(recordRequest);
			recordDTO=recordService.createRecord(recordDTO);
			
			map.put("result", recordDTO);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// boardResponse를 받아와서 post // postman 입력 시 BoardResponse의 변수를 사용해야한다.
	@PostMapping("/boards")
	public ResponseEntity<?> createRecords(@RequestBody BoardResponse boardResponse){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(boardResponse);
			recordDTO=recordService.createRecord(recordDTO);
			
			map.put("result", recordDTO);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 board를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// reviewResponse를 받아와서 post // postman 입력 시 ReviewResponse의 변수를 사용해야한다.
	@PostMapping("/reviews") 
	public ResponseEntity<?> createRecords(@RequestBody ReviewResponse reviewResponse){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(reviewResponse);
			
			Long bid=reviewResponse.getBid();
			RecordDTO boardDTOInRecordDTO=recordService.findFirstByBoardid(bid);
	
			recordDTO.setBoardTitle(boardDTOInRecordDTO.getBoardTitle());
			recordDTO.setBoardContent(boardDTOInRecordDTO.getBoardContent());
			recordDTO.setBoardCreateDate(boardDTOInRecordDTO.getBoardCreateDate());
			
			recordDTO=recordService.createRecord(recordDTO);
		
			map.put("result", recordDTO);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 review를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// record에 put한 것을 create
	@PutMapping("/records")
	public ResponseEntity<?> createUpdateRecords(@RequestBody RecordRequest recordRequest){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(recordRequest);
			recordDTO=recordService.createRecord(recordDTO);
			
			map.put("result", recordDTO);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// board에 put한 것을 create
	@PutMapping("/boards") 
	public ResponseEntity<?> createUpdateRecords(@RequestBody BoardResponse boardResponse){

		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(boardResponse);
			recordDTO=recordService.createRecord(recordDTO);
			
			map.put("result", recordDTO);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 board를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// review에 put한 것을 create
	@PutMapping("/reviews") 
	public ResponseEntity<?> createUpdateRecords(@RequestBody ReviewResponse reviewResponse){
		
		Map<String, Object> map=new HashMap<>();
		try {
			RecordDTO recordDTO=RecordDTO.toRecordDTO(reviewResponse);
			
			Long bid=reviewResponse.getBid();
			RecordDTO boardDTOInRecordDTO=recordService.findFirstByBoardid(bid);

			recordDTO.setBoardTitle(boardDTOInRecordDTO.getBoardTitle());
			recordDTO.setBoardContent(boardDTOInRecordDTO.getBoardContent());
			recordDTO.setBoardCreateDate(boardDTOInRecordDTO.getBoardCreateDate());
			
			recordDTO=recordService.createRecord(recordDTO);

			map.put("result", recordDTO);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result","record에 review를 생성하지 못했습니다.");
			
			return ResponseEntity.badRequest().body(map);
		}
	}
}
