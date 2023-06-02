package kr.co.tj.recordservice.persistance;

import java.io.Serializable;
import java.util.Date;

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
	private Long id; // record의 아이디

	private Long boardid; // 판매글의 아이디
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
	
	private Date recordCreateDate;
}
