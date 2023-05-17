package kr.co.tj.qnaservice.service;

<<<<<<< HEAD
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
=======
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.tj.qnaservice.dto.QnaDTO;
import kr.co.tj.qnaservice.dto.QnaEntity;
import kr.co.tj.qnaservice.dto.QnaResponse;
import kr.co.tj.qnaservice.jpa.QnaRepository;


@Service
public class QnaService {
	
	@Autowired
	private QnaRepository qnaRepository;

	
	
	private QnaDTO getDate(QnaDTO qnaDTO) {
		
		
		Date now = new Date();
		
		if(qnaDTO.getCreateDate() == null) {
			qnaDTO.setCreateDate(now);
		}
		qnaDTO.setUpdateDate(now);
				
		return qnaDTO;
	}

	
	public QnaDTO insert(QnaDTO qnaDTO) {
		
		qnaDTO.setId(qnaDTO.getId());
		qnaDTO = getDate(qnaDTO);
		qnaDTO.setTitle(qnaDTO.getTitle());
		qnaDTO.setContent(qnaDTO.getContent());
		
		QnaEntity qnaEntity = qnaDTO.toQnaEntity();
		qnaEntity = qnaRepository.save(qnaEntity);
		
		
		return qnaDTO;
	}


	// 수정
	@Transactional
	public QnaDTO update(QnaResponse qnaResponse) {
		
		 Optional<QnaEntity> optional = qnaRepository.findById(qnaResponse.getId());
		 
		 
		 if(!optional.isPresent()) {
			 throw new RuntimeException("해당 데이터가 없습니다.");
		 }
		 QnaEntity entity = optional.get();
		 
		 Date now = new Date();
		 
		 entity.setTitle(qnaResponse.getTitle());
		 entity.setContent(qnaResponse.getContent());
		 entity.setUpdateDate(now);
		 
		 entity = qnaRepository.save(entity);
		 
		 QnaDTO qnaDTO = QnaDTO.toQnaDTO(entity);
		 
		return qnaDTO;
	}


	// 삭제
	public void delete(Long id) {
	 qnaRepository.deleteById(id);
		
	}


	// 페이징
	public List<QnaResponse> getPage(Pageable pageable) {
		
		Page<QnaEntity> page = qnaRepository.findAll(pageable);
		
		List<QnaResponse> list = new ArrayList<>();
		
		list.forEach(e -> {
			QnaDTO qnaDTO = new QnaDTO();
			QnaResponse qnaResponse = qnaDTO.tpQnaFindResponse(e);
			list.add(qnaResponse);
		});
		
		return list;
	}


	// 목록
	public List<QnaDTO> findAll() {
		
		List<QnaEntity> entity = qnaRepository.findAll();
		List<QnaDTO> dto = new ArrayList<>();
		
		for (QnaEntity e: entity) {
			dto.add(QnaDTO.toQnaDTO(e));
		}
		
		return dto;
	}


	public QnaDTO findById(Long id) {

		Optional<QnaEntity> optional = qnaRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("게시물 가져오기 실패");
		}
		
		QnaEntity entity = optional.get();
		
		return QnaDTO.toQnaDTO(entity);
	}
>>>>>>> b0db4c1e9951c59ddf634a5a09983b1f30746a06
	
	
	
}
