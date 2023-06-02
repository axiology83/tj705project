package kr.co.tj.recordservice.service;

import java.util.List;

import kr.co.tj.recordservice.dto.RecordDTO;

public interface RecordService {
	RecordDTO createRecord(RecordDTO recordDTO);

	RecordDTO findFirstByBoardid(Long bid);

	List<RecordDTO> findAll();

	RecordDTO findById(Long id);

}
