package kr.co.tj.qnaservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
}
