package kr.co.tj.recordservice.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, Long>{

	Optional<RecordEntity> findFirstByBoardId(Long bid);

	RecordEntity findById(String id);

}
