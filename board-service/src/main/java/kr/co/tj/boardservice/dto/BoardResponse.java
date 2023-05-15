package kr.co.tj.boardservice.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private Long cid;
	
	private String username;

	private String title;

	private String content;
	
	private Date createAt;
	
	private Date updateAt;
	
	private Long readCnt;
	
	private String cateName;
	
}
