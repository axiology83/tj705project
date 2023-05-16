package kr.co.tj.recordservice.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="records")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id; // record의 아이디
	
	@Column(nullable =false)
	private Long boardId; // 판매글의 아이디
	
	@Column(nullable =false)
	private String seller; // 판메자의 닉네임 // feign 때문에 변수 통일
	
	@Column(nullable =false)
	private String buyer; // 구매자의 닉네임

	private Boolean hasChat; // 채팅 여부
	
	private Boolean hasLike; // 찜 여부
	
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float reviewRate;
	private Date reviewCreateDate;
	private Date reviewUpdateDate;
	
	private StatusOfBoard status; //판매중/예약/판매완료W
}
