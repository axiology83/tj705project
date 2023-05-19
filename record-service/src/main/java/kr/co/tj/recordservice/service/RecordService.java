package kr.co.tj.recordservice.service;

import kr.co.tj.recordservice.dto.RecordDTO;

public interface RecordService {
	RecordDTO createRecord(RecordDTO recordDTO);

	RecordDTO findFirstByBoardId(Long bid);
}
