package kr.co.tj.recordservice.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="record")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // record의 아이디
	
	@Column(nullable =false)
	private Long boardId; // 판매글의 아이디
	
	@Column(nullable =false)
	private String seller; // 판메자의 닉네임
	
	@Column(nullable =false)
	private String buyer; // 구매자의 닉네임
	
	@ColumnDefault("false")
	private Boolean hasChat; // 채팅 여부
	
	@ColumnDefault("false")
	private Boolean hasLike; // 찜 여부
	
	private String reviewTitle; 	// 리뷰 작성 시 내용을 저장
	private String reviewContent; 	// reviewEntity에서 삭제되어도 리뷰가 남아있을 수 있게 
	private Float reviewRate;
	private Date reviewCreateDate;
	private Date reviewUpdateDate;
	
	@Column(nullable =false)
	private String Status; // 판매중/예약/판매완료
}
