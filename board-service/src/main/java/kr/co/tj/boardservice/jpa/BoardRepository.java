package kr.co.tj.boardservice.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.boardservice.dto.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{



	void deleteByUsername(String username);

	List<BoardEntity> findByUsername(String username);

	Page<BoardEntity> findByTitleContainingOrContentContaining(String keyword, String keyword2, Pageable pageable);



	

}
