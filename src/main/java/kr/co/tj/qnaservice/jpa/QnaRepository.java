package kr.co.tj.qnaservice.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.qnaservice.dto.QnaEntity;

public interface QnaRepository extends JpaRepository<QnaEntity, Long> {

	List<QnaEntity> findByUsername(String username);

}
