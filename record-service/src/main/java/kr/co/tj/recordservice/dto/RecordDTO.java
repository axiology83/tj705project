package kr.co.tj.recordservice.dto;

import java.io.Serializable;
import java.util.Date;

import kr.co.tj.recordservice.persistance.RecordEntity;
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
	
	private Long id;
	
	private Long boardid; // 판매글의 아이디
	private String seller; // 판메자의 닉네임 
	private String buyer; // 구매자의 닉네임
	private String boardTitle;
	private String boardContent;
	// private Boolean hasChat; // 채팅 여부
	// private Boolean hasLike; // 찜 여부
	private Date boardCreateDate;
	
	private Long reviewId;
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float rate;
	private Date reviewCreateDate;

	private Date recordCreateDate;

	public static RecordDTO toRecordDTO(RecordEntity recordEntity) {
		
		return RecordDTO.builder()
				.id(recordEntity.getId())
		
				.boardid(recordEntity.getBoardid())
				.seller(recordEntity.getSeller())
				.buyer(recordEntity.getBuyer())
				.boardTitle(recordEntity.getBoardTitle())
				.boardContent(recordEntity.getBoardContent())
				.boardCreateDate(recordEntity.getBoardCreateDate())
				
				.reviewId(recordEntity.getReviewId())
				.reviewTitle(recordEntity.getReviewTitle())
				.reviewContent(recordEntity.getReviewContent())
				.rate(recordEntity.getRate())
				.reviewCreateDate(recordEntity.getReviewCreateDate())
				
				.recordCreateDate(recordEntity.getRecordCreateDate())

				.build();
	}
	
	public static RecordDTO toRecordDTO(RecordRequest recordRequest) {
		
		return RecordDTO.builder()
				
				.boardid(recordRequest.getBoardid())
				.seller(recordRequest.getSeller())
				.buyer(recordRequest.getBuyer())
				.boardTitle(recordRequest.getBoardTitle())
				.boardContent(recordRequest.getBoardContent())
				// .hasChat(recordRequest.getHasChat())
				// .hasLike(recordRequest.getHasLike())
				.boardCreateDate(recordRequest.getBoardCreateDate())
				
				.reviewId(recordRequest.getReviewId())
				.reviewTitle(recordRequest.getReviewTitle())
				.reviewContent(recordRequest.getReviewContent())
				.rate(recordRequest.getRate())
				.reviewCreateDate(recordRequest.getReviewCreateDate())
				
				.build();
	}


	
	public RecordEntity toRecordEntity() {
		// TODO Auto-generated method stub
		return RecordEntity.builder()
				.id(id)
				
				.boardid(boardid)
				.seller(seller)
				.buyer(buyer)
				.boardTitle(boardTitle)
				.boardContent(boardContent)
				// .hasChat(hasChat)
				// .hasLike(hasLike)
				.boardCreateDate(boardCreateDate)
				
				.reviewId(reviewId)
				.reviewTitle(reviewTitle)
				.reviewContent(reviewContent)
				.rate(rate)
				.reviewCreateDate(reviewCreateDate)
				
				.recordCreateDate(recordCreateDate)
				
				.build();
	}

	public static RecordDTO toRecordDTO(ReviewResponse reviewResponse) {
		return RecordDTO.builder()
				.boardid(reviewResponse.getBid())
				.seller(reviewResponse.getSellerId())
				.buyer(reviewResponse.getBuyerName())
				
				.reviewId(reviewResponse.getId())
				.reviewTitle(reviewResponse.getTitle())
				.reviewContent(reviewResponse.getContent())
				.rate(reviewResponse.getRate())
				.reviewCreateDate(reviewResponse.getCreateDate())
				
				.build();
	}

	public static RecordDTO toRecordDTO(BoardResponse boardResponse) {
		return RecordDTO.builder()
				
				.boardid(boardResponse.getId())
				.boardTitle(boardResponse.getTitle())
				.seller(boardResponse.getUsername())
				.boardContent(boardResponse.getContent())
				.boardCreateDate(boardResponse.getCreateDate())
				
				.build();
	}
}
