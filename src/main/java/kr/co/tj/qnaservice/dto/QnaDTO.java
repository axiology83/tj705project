package kr.co.tj.qnaservice.dto;

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
public class QnaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id; // qna 게시물 아이디
	
	private String username; // 작성자
	
	private String title;  // 제목
	
	private String content; // 내용
	
	private Date createDate; // 작성일
	
	private Date updateDate; // 수정일

	public static QnaDTO toQnaDTO(QnaRequest qnaRequest) {

		return QnaDTO.builder()
				.id(qnaRequest.getId())
				.username(qnaRequest.getUsername())
				.title(qnaRequest.getTitle())
				.content(qnaRequest.getContent())
				.build();
	}

	public QnaResponse toQnaResponse() {
		
		return QnaResponse.builder()
				.id(id)
				.username(username)
				.title(title)
				.content(content)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}

	public QnaEntity toQnaEntity() {
		
		return QnaEntity.builder()
				.id(id)
				.username(username)
				.title(title)
				.content(content)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}

	public static QnaDTO toQnaDTO(QnaEntity e) {
		
		return QnaDTO.builder()
				.username(e.getUsername())
				.id(e.getId())
				.title(e.getTitle())
				.content(e.getContent())
				.createDate(e.getCreateDate())
				.updateDate(e.getUpdateDate())
				.build();
	}

	public QnaResponse tpQnaFindResponse(QnaResponse e) {

		return QnaResponse.builder()
				.username(e.getUsername())
				.id(e.getId())
				.title(e.getTitle())
				.content(e.getContent())
				.createDate(e.getCreateDate())
				.updateDate(e.getUpdateDate())
				.build();
	}
	
	
	
}
