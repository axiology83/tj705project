package kr.co.tj.replyservice.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name="reply")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyEntity implements Serializable{
   
   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 댓글의 고유 id
   
   @Column	
	private Long qnaId; // 게시물의 고유 id = bid
   
	private Long parId; // 대댓글의 고유 id = 부모 댓글의 고유 id 
   
   @Column(nullable =false)
	private String username; // 작성자
	
   @Column(nullable =false)
	private String content; // 내용
   
	private Date createDate; // 작성일
	private Date updateDate; // 수정일
	


}