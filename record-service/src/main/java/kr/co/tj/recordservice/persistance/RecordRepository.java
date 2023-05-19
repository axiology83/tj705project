package kr.co.tj.recordservice.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<RecordEntity, Long>{

}
