package kr.co.tj.reviewservice.dto;

import java.io.Serializable;
import java.util.Date;

import kr.co.tj.reviewservice.persistence.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id; // 리뷰게시글 번호

	private String sellerId; // 판매자 username

	private String buyerName; // 구매자 username

	private String title;

	private String content;

	private int count;

	private float rate;

	private Date createDate;

	private Date updateDate;
	

	// 서비스에서 저장한 값을 dto에 넣는작업
	public ReviewDTO toReviewDTO(ReviewEntity entity) {
				this.id = entity.getId();
				this.sellerId = entity.getSellerId();
				this.buyerName = entity.getBuyerName();
				this.title = entity.getTitle();
				this.content = entity.getContent();
				this.rate = entity.getRate();
				this.count = entity.getCount();
		
		return this;
	}

	public ReviewEntity toReviewEntity() {
		
		return ReviewEntity.builder()
				.id(id)
				.sellerId(sellerId)
				.buyerName(buyerName)
				.title(title)
				.content(content)
				.count(count)
				.rate(rate)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}

	// 입력받은 값을 dto에 넣는작업
	public static ReviewDTO toReviewDTOreq(ReviewRequest reviewRequest) {
		
		return ReviewDTO.builder()
				.id(reviewRequest.getId())	//수정을위해 id추가
				.sellerId(reviewRequest.getSellerId())
				.buyerName(reviewRequest.getBuyerName())
				.title(reviewRequest.getTitle())
				.content(reviewRequest.getContent())
				.rate(reviewRequest.getRate())
				.build();
	}
	
	// 저장한 dto값을 response에 넘김
	public ReviewResponse toReviewResponse() {
		
		return ReviewResponse.builder()
				.id(id)
				.sellerId(sellerId)
				.buyerName(buyerName)
				.title(title)
				.content(content)
				.count(count)
				.rate(rate)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	

		
	}

	public ReviewResponse toReviewFindResponse(ReviewEntity x) {
		
		return ReviewResponse.builder()
				.id(x.getId())
				.sellerId(x.getSellerId())
				.buyerName(x.getBuyerName())
				.title(x.getTitle())
				.content(x.getContent())
				.count(x.getCount())
				.rate(x.getRate())
				.createDate(x.getCreateDate())
				.updateDate(x.getUpdateDate())
				.build();
	}

}
