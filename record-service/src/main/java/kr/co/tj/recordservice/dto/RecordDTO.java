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
public class RecordDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long boardId; // 판매글의 아이디
	private String username; // 판메자의 닉네임 // feign 때문에 변수 통일
	private String buyer; // 구매자의 닉네임
	private Boolean hasChat; // 채팅 여부
	private Boolean hasLike; // 찜 여부
	
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float reviewRate;
	private Date reviewCreateDate;
	private Date reviewUpdateDate;
	
	private StatusOfBoard status;

	public static RecordDTO toRecordDTO(RecordRequest recordRequest) {
		
		return RecordDTO.builder()
				.boardId(recordRequest.getBoardId())
				.username(recordRequest.getUsername())
				.buyer(recordRequest.getBuyer())
				.hasChat(recordRequest.getHasChat())
				.hasLike(recordRequest.getHasLike())
				.reviewTitle(recordRequest.getReviewTitle())
				.reviewContent(recordRequest.getReviewContent())
				.reviewRate(recordRequest.getReviewRate())
				.reviewCreateDate(recordRequest.getReviewCreateDate())
				.reviewUpdateDate(recordRequest.getReviewUpdateDate())
				.status(recordRequest.getStatus())
				.build();
	}

	public RecordResponse toRecordResponse() {

		return RecordResponse.builder()
				.boardId(boardId)
				.username(username)
				.buyer(buyer)
				.hasChat(hasChat)
				.hasLike(hasLike)
				.reviewTitle(reviewTitle)
				.reviewContent(reviewContent)
				.reviewRate(reviewRate)
				.reviewCreateDate(reviewCreateDate)
				.reviewUpdateDate(reviewUpdateDate)
				.status(status)
				.build();
	}

	public RecordEntity toRecordEntity() {
		// TODO Auto-generated method stub
		return RecordEntity.builder()
				.boardId(boardId)
				.username(username)
				.buyer(buyer)
				.hasChat(hasChat)
				.hasLike(hasLike)
				.reviewTitle(reviewTitle)
				.reviewContent(reviewContent)
				.reviewRate(reviewRate)
				.reviewCreateDate(reviewCreateDate)
				.reviewUpdateDate(reviewUpdateDate)
				.status(status)
				.build();
	}

	public static RecordDTO toRecordDTO(ReviewResponse reviewResponse) {
		return RecordDTO.builder()
				.boardId(reviewResponse.getBoardId())
				.username(reviewResponse.getUsername())
				.buyer(reviewResponse.getBuyer())
				.hasChat(reviewResponse.getHasChat())
				.hasLike(reviewResponse.getHasLike())
				.reviewTitle(reviewResponse.getReviewTitle())
				.reviewContent(reviewResponse.getReviewContent())
				.reviewRate(reviewResponse.getReviewRate())
				.reviewCreateDate(reviewResponse.getReviewCreateDate())
				.reviewUpdateDate(reviewResponse.getReviewUpdateDate())
				.status(reviewResponse.getStatus())
				.build();
	}

	public static RecordDTO toRecordDTO(BoardResponse boardResponse) {
		return RecordDTO.builder()
				.boardId(boardResponse.getBoardId())
				.username(boardResponse.getUsername())
				.buyer(boardResponse.getBuyer())
				.hasChat(boardResponse.getHasChat())
				.hasLike(boardResponse.getHasLike())
				.reviewTitle(boardResponse.getReviewTitle())
				.reviewContent(boardResponse.getReviewContent())
				.reviewRate(boardResponse.getReviewRate())
				.reviewCreateDate(boardResponse.getReviewCreateDate())
				.reviewUpdateDate(boardResponse.getReviewUpdateDate())
				.status(boardResponse.getStatus())
				.build();
	}
}
