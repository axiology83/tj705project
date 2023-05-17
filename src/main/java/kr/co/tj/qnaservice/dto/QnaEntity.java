package kr.co.tj.qnaservice.dto;

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
@Table(name="qna")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaEntity implements Serializable{
   
   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // qna 게시물 아이디
	
   @Column(nullable =false)
	private String username; // 작성자
	
   @Column(nullable =false)
	private String title;  // 제목
	
   @Column(nullable =false)
	private String content; // 내용
	
   
	private Date createDate; // 작성일
	private Date updateDate; // 수정일
}