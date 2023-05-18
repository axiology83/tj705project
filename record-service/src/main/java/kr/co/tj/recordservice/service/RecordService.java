package kr.co.tj.recordservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.recordservice.dto.RecordDTO;
import kr.co.tj.recordservice.dto.RecordEntity;
import kr.co.tj.recordservice.jpa.RecordRepository;

@Service
public class RecordService {

	
	@Autowired
	private RecordRepository recordRepository;
	
	public RecordDTO createRecord(RecordDTO recordDTO) {
		
//		 if(recordDTO.getHasChat()==null) {	// recordRequest로부터 가져온 recordDTO의 chat이 null이면 false 반환
//			recordDTO.setHasChat(false);
//		}
//		
//		 if(recordDTO.getHasLike()==null) {  // recordRequest로부터 가져온 recordDTO의 like가 null이면 false 반환
//			recordDTO.setHasLike(false);
//		}
		
		RecordEntity recordEntity=recordDTO.toRecordEntity();
		
		recordEntity=recordRepository.save(recordEntity);
	
		return recordDTO;
	}

	public boolean existsByBoardIdAndBuyer(Long boardId, String buyer) {
		return recordRepository.existsByBoardIdAndBuyer(boardId, buyer);
	}
}
