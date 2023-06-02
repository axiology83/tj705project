package kr.co.tj.recordservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.recordservice.dto.RecordDTO;
import kr.co.tj.recordservice.persistance.RecordEntity;
import kr.co.tj.recordservice.persistance.RecordRepository;

@Service
public class RecordServiceImpl implements RecordService{

	public RecordDTO getDate(RecordDTO recordDTO) {
		Date date = new Date();
		
		if(recordDTO.getRecordCreateDate() == null) {
			recordDTO.setRecordCreateDate(date);
		}
		
		return recordDTO;
	}
	
	@Autowired
	private RecordRepository recordRepository;
	
	public RecordDTO createRecord(RecordDTO recordDTO) {
		recordDTO=getDate(recordDTO);
		RecordEntity recordEntity=recordDTO.toRecordEntity();
		recordEntity=recordRepository.save(recordEntity);
		
		return RecordDTO.toRecordDTO(recordEntity);
	}
	
	public RecordDTO findFirstByBoardid(Long bid) {
		Optional<RecordEntity> optional = recordRepository.findFirstByBoardid(bid);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("존재하지 않는 정보입니다.");
		}
		
		RecordEntity entity = optional.get();
		
		return RecordDTO.toRecordDTO(entity);
	}
	

	@Override
	public List<RecordDTO> findAll() {
		List<RecordEntity> list_entity = recordRepository.findAll();
		List<RecordDTO> list_response = new ArrayList<>();
		
		for (RecordEntity x : list_entity) {
			list_response.add(RecordDTO.toRecordDTO(x));
		}
		return list_response;
	}
	
	public RecordDTO findById(Long id) {
		Optional<RecordEntity> optional =recordRepository.findById(id);
		RecordEntity recordEntity=optional.get();
		return RecordDTO.toRecordDTO(recordEntity);
	}
}
