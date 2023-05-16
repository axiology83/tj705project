package kr.co.tj.boardservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.tj.boardservice.dto.BoardDTO;
import kr.co.tj.boardservice.dto.BoardEntity;
import kr.co.tj.boardservice.dto.BoardResponse;
import kr.co.tj.boardservice.dto.CategoryResponse;
import kr.co.tj.boardservice.feign.CategoryFeign;
import kr.co.tj.boardservice.jpa.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public BoardDTO createBoard(BoardDTO boardDTO) {
		
		boardDTO = getDate(boardDTO);
		boardDTO.setReadCnt(0L);
		
		BoardEntity boardEntity = boardDTO.toBoardEntity();
		
		boardEntity = boardRepository.save(boardEntity);
		
		
		return boardDTO.toBoardDTO2(boardEntity);
				
	}
	
	private  BoardDTO getDate(BoardDTO boardDTO) {
		Date now = new Date();
		
		if(boardDTO.getCreateDate() ==null) {
			boardDTO.setCreateDate(now);
		}
		boardDTO.setUpdateDate(now);
		
		return boardDTO;
	}

	 public BoardDTO findById(Long id) {
		
		Optional<BoardEntity> optional = boardRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("잘못된 정보입니다");
		}
		
		BoardEntity entity = optional.get();
		
		
		return BoardDTO.toBoardDTO(entity);
	}

	public List<BoardDTO> findAll() {
		
		List<BoardEntity> list = boardRepository.findAll();
		List<BoardDTO> dtoList = new ArrayList<>();
		
		for(BoardEntity entity : list) {
			dtoList.add(BoardDTO.toBoardDTO(entity));
		}
		
		return dtoList;
	}
	
	public void delete(Long id) {
		
	
		
		boardRepository.deleteById(id);	
	}

	
	
	@Transactional
	public BoardDTO update(BoardResponse boardResponse) {
		Optional<BoardEntity> optional = boardRepository.findById(boardResponse.getId());
		
		if(!optional.isPresent()) {
			throw new RuntimeException("값을 입력해주세요");
		}
		
		BoardEntity boardEntity = optional.get();
		
		Date now = new Date();
		
		boardEntity.setTitle(boardResponse.getTitle());
		boardEntity.setContent(boardResponse.getContent());
		boardEntity.setCid(boardResponse.getCid());
		boardEntity.setUpdateDate(now);
		
		 boardEntity = boardRepository.save(boardEntity);
		 
		 BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
		
		return boardDTO;
	}

	public Page<BoardDTO> findAll(Integer page) {
		List<Sort.Order> sortList = new ArrayList<>();
		sortList.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sortList));
		
		Page<BoardEntity> page_board = boardRepository.findAll(pageable);
		
		Page<BoardDTO> page_dto = page_board.map(
				boardEntity ->
				new BoardDTO(boardEntity.getId(),
						boardEntity.getCid(),
						boardEntity.getUsername(),
						boardEntity.getTitle(),
						boardEntity.getContent(),
						boardEntity.getCreateDate(),
						boardEntity.getUpdateDate(),
						boardEntity.getReadCnt(),
						boardEntity.getCateName()
						
						
						)
				
				
				
				);
				
		return page_dto;
	}

	public Page<BoardDTO> search(int pageNum, String keyword) {
		List<Sort.Order> sortList = new ArrayList<>();
		sortList.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(sortList));
				
		Page<BoardEntity> page_board = boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
		 
		 Page<BoardDTO> page_dto = page_board.map(
				 
				 boardEntity ->
				 new BoardDTO(
						 boardEntity.getId(),
						 boardEntity.getCid(),
						 boardEntity.getUsername(),
						 boardEntity.getTitle(),
						 boardEntity.getContent(),
						 boardEntity.getCreateDate(),
						 boardEntity.getUpdateDate(),
						 boardEntity.getReadCnt(),
						 boardEntity.getCateName()
						 )
				 );
		 return page_dto;
		
	}
	
	@Transactional
	public BoardDTO readCntUpdate(BoardDTO dto) {
		Optional<BoardEntity> optional =  boardRepository.findById(dto.getId());
		
		BoardEntity orgEntity = optional.get();
		long readCnt = orgEntity.getReadCnt() +1 ;
		
		BoardEntity entity = BoardEntity.builder()
				.title(orgEntity.getTitle())
				.id(orgEntity.getId())
				.content(orgEntity.getContent())
				.username(orgEntity.getUsername())
				.readCnt(readCnt)
				.cid(orgEntity.getCid())
				.createDate(orgEntity.getCreateDate())
				.updateDate(orgEntity.getUpdateDate())
				.cateName(orgEntity.getCateName())
				.build();
		
		
		entity = boardRepository.save(entity);
		
		return BoardDTO.toBoardDTO(entity);
		
		
				}

	public List<BoardResponse> getPage(Pageable pageable) {
		Page<BoardEntity> entity = boardRepository.findAll(pageable);
		List<BoardResponse> boardResponse = new ArrayList<>();
		
		entity.forEach(x -> {
			BoardDTO dto  = new BoardDTO();
			BoardResponse res = dto.toBoardFindResponse(x);
			boardResponse.add(res);
		});
		return boardResponse;
	}

	
	


	

	
	
	

}
