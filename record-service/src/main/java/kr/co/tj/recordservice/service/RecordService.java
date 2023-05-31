package kr.co.tj.recordservice.service;

import java.util.List;

import kr.co.tj.recordservice.dto.RecordDTO;
import kr.co.tj.recordservice.dto.RecordResponse;

public interface RecordService {
	RecordDTO createRecord(RecordDTO recordDTO);

	RecordDTO findFirstByBoardId(Long bid);

	List<RecordResponse> findAll();

	RecordDTO findById(String id);
}
