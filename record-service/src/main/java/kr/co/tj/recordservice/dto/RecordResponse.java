package kr.co.tj.recordservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordResponse {
	private Long boardId; // 판매글의 아이디
	private String seller; // 판메자의 닉네임 // feign 때문에 변수 통일
	private String buyer; // 구매자의 닉네임
	private Boolean hasChat; // 채팅 여부
	private Boolean hasLike; // 찜 여부
	
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float reviewRate;
	private Date reviewCreateDate;
	private Date reviewUpdateDate;
	
	private StatusOfBoard status;
}
