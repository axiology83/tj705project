package kr.co.tj.recordservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import kr.co.tj.recordservice.dto.RecordDTO;
import kr.co.tj.recordservice.persistance.RecordEntity;
import kr.co.tj.recordservice.persistance.RecordRepository;

@Service
public class RecordServiceImpl implements RecordService{

	
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
	
	public RecordDTO findFirstByBoardId(Long bid) {
		Optional<RecordEntity> optional = recordRepository.findFirstByBoardId(bid);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("존재하지 않는 정보입니다.");
		}
		
		RecordEntity entity = optional.get();
		
		return RecordDTO.toRecordDTO(entity);
	}
}
