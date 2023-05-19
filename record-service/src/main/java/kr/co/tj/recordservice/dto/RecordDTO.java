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
	
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float rate;
	private Integer reviewCnt;
	private Date reviewCreateDate;
	private Date reviewUpdateDate;
	
	private Float sellerRateAvg;

	public static RecordDTO toRecordDTO(RecordRequest recordRequest) {
		
		return RecordDTO.builder()
				
				.cateId(recordRequest.getCateId())
				.cateName(recordRequest.getCateName())
				
				.boardId(recordRequest.getBoardId())
				.seller(recordRequest.getSeller())
				.buyer(recordRequest.getBuyer())
				.boardTitle(recordRequest.getBoardTitle())
				.boardContent(recordRequest.getBoardContent())
				.boardCnt(recordRequest.getBoardCnt())
				// .hasChat(recordRequest.getHasChat())
				// .hasLike(recordRequest.getHasLike())
				.status(recordRequest.getStatus())
				.boardCreateDate(recordRequest.getBoardCreateDate())
				.boardUpdateDate(recordRequest.getBoardUpdateDate())
				
				.reviewTitle(recordRequest.getReviewTitle())
				.reviewContent(recordRequest.getReviewContent())
				.rate(recordRequest.getRate())
				.reviewCnt(recordRequest.getReviewCnt())
				.reviewCreateDate(recordRequest.getReviewCreateDate())
				.reviewUpdateDate(recordRequest.getReviewUpdateDate())
				
				.sellerRateAvg(recordRequest.getSellerRateAvg())
				
				.build();
	}

	public RecordResponse toRecordResponse() {

		return RecordResponse.builder()
				.cateId(cateId)
				.cateName(cateName)
				
				.boardId(boardId)
				.seller(seller)
				.buyer(buyer)
				.boardTitle(boardTitle)
				.boardContent(boardContent)
				.boardCnt(boardCnt)
				// .hasChat(hasChat)
				// .hasLike(hasLike)
				.status(status)
				.boardCreateDate(boardCreateDate)
				.boardUpdateDate(boardUpdateDate)
				
				.reviewTitle(reviewTitle)
				.reviewContent(reviewContent)
				.rate(rate)
				.reviewCnt(reviewCnt)
				.reviewCreateDate(reviewCreateDate)
				.reviewUpdateDate(reviewUpdateDate)
				
				.sellerRateAvg(sellerRateAvg)
				
				.build();
	}

	public RecordEntity toRecordEntity() {
		// TODO Auto-generated method stub
		return RecordEntity.builder()
				.cateId(cateId)
				.cateName(cateName)
				
				.boardId(boardId)
				.seller(seller)
				.buyer(buyer)
				.boardTitle(boardTitle)
				.boardContent(boardContent)
				.boardCnt(boardCnt)
				// .hasChat(hasChat)
				// .hasLike(hasLike)
				.status(status)
				.boardCreateDate(boardCreateDate)
				.boardUpdateDate(boardUpdateDate)
				.reviewCnt(reviewCnt)
				.reviewTitle(reviewTitle)
				.reviewContent(reviewContent)
				.rate(rate)
				
				.reviewCreateDate(reviewCreateDate)
				.reviewUpdateDate(reviewUpdateDate)
				
				.sellerRateAvg(sellerRateAvg)
				
				.build();
	}

	public static RecordDTO toRecordDTO(ReviewResponse reviewResponse) {
		return RecordDTO.builder()
				.boardId(reviewResponse.getId())
				.seller(reviewResponse.getSellerId())
				.buyer(reviewResponse.getBuyerName())
				
				.reviewTitle(reviewResponse.getTitle())
				.reviewContent(reviewResponse.getContent())
				.rate(reviewResponse.getRate())
				.reviewCnt(reviewResponse.getCount())
				.reviewCreateDate(reviewResponse.getCreateDate())
				.reviewUpdateDate(reviewResponse.getUpdateDate())
				
				.build();
	}

	public static RecordDTO toRecordDTO(BoardResponse boardResponse) {
		return RecordDTO.builder()
				
				.cateId(boardResponse.getCid())
				.cateName(boardResponse.getCateName())
				
				.boardId(boardResponse.getId())
				.boardTitle(boardResponse.getTitle())
				.seller(boardResponse.getUsername())
				.boardContent(boardResponse.getContent())
				// .hasChat(boardResponse.getHasChat())
				// .hasLike(boardResponse.getHasLike())
				// .status(boardResponse.getStatus())
				.boardCreateDate(boardResponse.getCreateDate())
				.boardUpdateDate(boardResponse.getUpdateDate())
				.boardCnt(boardResponse.getReadCnt())
				
				.build();
	}
}
