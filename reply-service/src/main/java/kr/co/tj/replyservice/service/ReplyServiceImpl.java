package kr.co.tj.replyservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import kr.co.tj.replyservice.dto.ReplyDTO;
import kr.co.tj.replyservice.dto.ReplyResponse;
import kr.co.tj.replyservice.persistence.ReplyEntity;
import kr.co.tj.replyservice.persistence.ReplyRepository;

@Service
public class ReplyServiceImpl implements ReplyService{
	

		@Autowired
		private ReplyRepository replyRepository;
		
		public ReplyDTO getDate(ReplyDTO dto) {
			
			Date now = new Date();
			
			if(dto.getCreateDate() == null) {
				dto.setCreateDate(now);
			}
			dto.setUpdateDate(now);	
			return dto;
		}


		// 댓글 입력
		public ReplyDTO insert(ReplyDTO dto) {
		
			dto.setId(dto.getId());
			dto= getDate(dto);
			dto.setQnaId(dto.getQnaId());
			dto.setParId(dto.getParId());
			dto.setContent(dto.getContent());

			
			ReplyEntity entity = dto.toReplyEntity();
			entity = replyRepository.save(entity);
			
			return dto;
		}

		// 댓글 수정
		public ReplyDTO update(ReplyResponse replyResponse) {
			
			Optional<ReplyEntity> optional = replyRepository.findById(replyResponse.getId());
			
			if(!optional.isPresent()) {
				 throw new RuntimeException("해당 데이터가 없습니다.");
			 }
			ReplyEntity entity = optional.get();
			
			Date now = new Date();
			
			entity.setContent(replyResponse.getContent());
			entity.setUpdateDate(now);
			
			entity = replyRepository.save(entity);
			
			ReplyDTO dto = ReplyDTO.toReplyDTO(entity);
			
			return dto;
		}

		
		// 댓글 삭제
		public void delete(Long id) {
		    Optional<ReplyEntity> optional = replyRepository.findById(id);
		    
		    if(optional.isPresent()) {
		        ReplyEntity entity = optional.get();
		        Long parId = entity.getParId();

		        replyRepository.deleteById(id);
		        
		        if(parId != null) {
		            List<ReplyEntity> list = replyRepository.findByParId(parId);
		            if(list.isEmpty()) {
		                //대댓글이 없는 경우 부모 댓글도 삭제
		                replyRepository.deleteById(parId);
		            }
		        }
		    } else {
		        throw new RuntimeException("해당하는 댓글이 없습니다.");
		    }    
		}


		// 게시물 id를 받아서 댓글 전체 목록 확인하기
		public List<ReplyDTO> findByQnaId(Long qnaId) {
		    List<ReplyEntity> list = replyRepository.findByQnaId(qnaId);
		    List<ReplyDTO> dto = new ArrayList<>();

		    for (ReplyEntity entity: list) {
		        dto.add(ReplyDTO.toReplyDTO(entity));
		    }

		    return dto;
		}

		
		public ReplyDTO findById(Long id) {
		    Optional<ReplyEntity> optional = replyRepository.findById(id);

		    if(!optional.isPresent()){
		        throw new RuntimeException("댓글 가져오기 실패");
		    }
		    	ReplyEntity entity = optional.get();
		        return ReplyDTO.toReplyDTO(entity);
		}

		// 부모댓글 찾기 (대댓글이 생성되어 기존 댓글이 자동으로 부모댓글이 된 경우 작동)
		public List<ReplyDTO> findByParId(Long parId) {
		    List<ReplyEntity> list = replyRepository.findByParId(parId);
		    List<ReplyDTO> dto = new ArrayList<>();

		    for (ReplyEntity entity: list) {
		        dto.add(ReplyDTO.toReplyDTO(entity));
		    }
		    return dto;
		}
		
		


}
