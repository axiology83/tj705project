package kr.co.tj.qnaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaRequest {
	
	private Long id; // 게시물 아이디
	
	private String username; // 작성자
	
	private String title;  // 제목
	
	private String content; // 내용
	


}
