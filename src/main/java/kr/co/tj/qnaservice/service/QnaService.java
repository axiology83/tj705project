package kr.co.tj.qnaservice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import kr.co.tj.qnaservice.dto.QnaDTO;
import kr.co.tj.qnaservice.dto.QnaResponse;

public interface QnaService {

	QnaDTO getDate(QnaDTO qnaDTO);
	
	QnaDTO insert(QnaDTO qnaDTO);
	
	QnaDTO update(QnaResponse qnaResponse);
	
	void delete(Long id);
	
	List<QnaResponse> getPage(Pageable pageable);
	
	List<QnaDTO> findAll();
	
	QnaDTO findById(Long id);
	
	
	
}
