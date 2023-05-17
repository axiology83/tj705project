package kr.co.tj.replyservice.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

	List<ReplyEntity> findByQnaId(Long qnaId);

	List<ReplyEntity> findByParId(Long parId);




	

}
