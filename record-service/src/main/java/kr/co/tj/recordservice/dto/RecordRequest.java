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
public class RecordRequest {

	private Long Boardid; // 판매글의 아이디
	private String seller; // 판메자의 닉네임 
	private String buyer; // 구매자의 닉네임
	private String boardTitle;
	private String boardContent;
	private Date boardCreateDate;
	
	private Long reviewId;
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float rate;
	private Date reviewCreateDate;
	
	private Float sellerRateAvg;
}
