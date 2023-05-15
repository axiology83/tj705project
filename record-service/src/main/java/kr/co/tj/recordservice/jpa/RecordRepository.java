package kr.co.tj.recordservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.recordservice.dto.RecordEntity;

public interface RecordRepository extends JpaRepository<RecordEntity, Long>{
	
}
