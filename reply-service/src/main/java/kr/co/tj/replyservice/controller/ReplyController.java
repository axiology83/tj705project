package kr.co.tj.replyservice.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import kr.co.tj.replyservice.dto.ReplyDTO;
import kr.co.tj.replyservice.dto.ReplyRequest;
import kr.co.tj.replyservice.dto.ReplyResponse;
import kr.co.tj.replyservice.service.ReplyService;

@RestController

@RequestMapping("/reply-service")
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	// 일반 댓글 
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		
		if(id == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 댓글이 없습니다");
		}
		ReplyDTO dto = replyService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	
	// 대댓글이 달렸을 경우 기본 댓글 id 가 부모아이디로 인식됨
	@GetMapping("/parId/{parId}")
	public ResponseEntity<?> findByParId(@PathVariable("parId") Long parId){
		
		if (parId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당하는 부모 댓글이 없습니다.");
		}
		List<ReplyDTO> dto = replyService.findByParId(parId);

		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	// 댓글 작성
	@PostMapping("/replies")
	public ResponseEntity<?> insert(@RequestBody ReplyRequest replyRequest) {
		
		ReplyDTO dto = ReplyDTO.toReplyDTO(replyRequest);
		dto = replyService.insert(dto);
		
		ReplyResponse replyResponse = dto.toReplyResponse();
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(replyResponse);
	}
	
	// 각 게시물 id 를 받은 댓글 리스트 
	@GetMapping("listall/{qnaId}")
	public ResponseEntity<?> findList(@PathVariable Long qnaId) {
		
		List<ReplyDTO> list = replyService.findByQnaId(qnaId);
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 댓글 수정
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ReplyResponse replyResponse) {
	
		ReplyDTO dto = replyService.update(replyResponse);
		replyResponse = dto.toReplyResponse();
		
		dto.setContent(replyResponse.getContent());
		dto.setUpdateDate(replyResponse.getUpdateDate());
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(replyResponse);
	}
	
	
	// 댓글 삭제
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody ReplyRequest replyRequest){
		
		
	try {
		replyService.delete(replyRequest.getId());
		
		return ResponseEntity.status(HttpStatus.OK).body("삭제 성공");
	} catch (Exception e) {
		e.printStackTrace();
	}	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
	}
	

	
	
}
