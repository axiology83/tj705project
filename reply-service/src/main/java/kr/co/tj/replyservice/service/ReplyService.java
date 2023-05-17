package kr.co.tj.replyservice.service;

import java.util.List;

import kr.co.tj.replyservice.dto.ReplyDTO;
import kr.co.tj.replyservice.dto.ReplyResponse;

public interface ReplyService {

	ReplyDTO getDate(ReplyDTO dto);
	
	ReplyDTO insert(ReplyDTO dto);
	
	ReplyDTO update(ReplyResponse replyResponse);
	
	void delete(Long id);
	
	List<ReplyDTO> findByQnaId(Long qnaId);
	
	ReplyDTO findById(Long id);
	
	List<ReplyDTO> findByParId(Long parId);
}
