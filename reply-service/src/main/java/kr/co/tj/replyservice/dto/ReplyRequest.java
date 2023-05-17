package kr.co.tj.replyservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyRequest {
	
	private Long id;
	
	private Long qnaId; // 게시물의 고유 id = bid
	
	private Long parId; // 대댓글의 고유 id = 부모 댓글의 고유 id 
	
	private String username;

	private String content;


}
