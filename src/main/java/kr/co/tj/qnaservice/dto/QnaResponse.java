package kr.co.tj.qnaservice.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaResponse implements Serializable{
		
	
	private static final long serialVersionUID=1L;
	
	private Long id;
	
	private String username; // 작성자
	
	private String title;  // 제목
	
	private String content; // 내용
	
	private Date createDate; // 작성일
	
	private Date updateDate; // 수정일


}
