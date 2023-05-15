package kr.co.tj.recordservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
	private Long boardId;
	private String seller;
	private String buyer;

	private String reviewTitle;
	private String reviewContent;
	private Float reviewRate;
	private Date reviewCreateDate;
	private Date reviewUpdateDate;
	
	private String Status; 
}

//review-service>dto>ReviewRequest

//private Long boardId;
//private String buyer;
//private String reviewTitle;
//private String reviewContent;
//private Float reviewRate;
