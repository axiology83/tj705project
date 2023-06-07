package kr.co.tj.recordservice.persistance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<RecordEntity, Long>{

	Optional<RecordEntity> findFirstByBoardid(Long bid);

	List<RecordEntity> findBySeller(String sellerId);

}
