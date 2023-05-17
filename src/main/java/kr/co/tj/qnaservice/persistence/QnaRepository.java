package kr.co.tj.qnaservice.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface QnaRepository extends JpaRepository<QnaEntity, Long> {

	List<QnaEntity> findByUsername(String username);

}
