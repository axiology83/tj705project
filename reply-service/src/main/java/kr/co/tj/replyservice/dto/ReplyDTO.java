package kr.co.tj.replyservice.dto;

import java.io.Serializable;
import java.util.Date;

import kr.co.tj.replyservice.persistence.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private Long qnaId; // 게시물의 고유 id = bid
	
	private Long id; // 댓글의 고유 id
	
	private Long parId; // 대댓글의 고유 id = 부모 댓글의 고유 id 
	
	private String username;

	private String content;
	
	private Date createDate;
	
	private Date updateDate;

	public static ReplyDTO toReplyDTO(ReplyRequest replyRequest) {
	
		return ReplyDTO.builder()
				.id(replyRequest.getId())
				.qnaId(replyRequest.getQnaId())
				.parId(replyRequest.getParId())
				.username(replyRequest.getUsername())
				.content(replyRequest.getContent())
				.build();
	}

	public ReplyEntity toReplyEntity() {
		
		return ReplyEntity.builder()
				.id(id)
				.qnaId(qnaId)
				.parId(parId)
				.username(username)
				.content(content)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}

	public ReplyResponse toReplyResponse() {
		
		return ReplyResponse.builder()
				.id(id)
				.qnaId(qnaId)
				.parId(parId)
				.username(username)
				.content(content)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}

	public static ReplyDTO toReplyDTO(ReplyEntity e) {
		
		return ReplyDTO.builder()
				.id(e.getId())
				.qnaId(e.getQnaId())
				.parId(e.getParId())
				.username(e.getUsername())
				.content(e.getContent())
				.createDate(e.getCreateDate())
				.updateDate(e.getUpdateDate())
				.build();
	}
}
