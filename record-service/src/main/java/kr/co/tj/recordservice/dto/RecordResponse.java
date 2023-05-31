package kr.co.tj.recordservice.dto;

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
public class RecordResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Long cateId;
	private String cateName;
	
	private Long boardId; // 판매글의 아이디
	private String seller; // 판메자의 닉네임 
	private String buyer; // 구매자의 닉네임
	private String boardTitle;
	private String boardContent;
	private Long boardCnt;
	// private Boolean hasChat; // 채팅 여부
	// private Boolean hasLike; // 찜 여부
	private StatusOfBoard status; // 판매중/예약/판매완료
	private Date boardCreateDate;
	private Date boardUpdateDate;
	
	private Long reviewId;
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float rate;
	private Integer reviewCnt;
	private Date reviewCreateDate;
	private Date reviewUpdateDate;
	
	private Float sellerRateAvg;
}
