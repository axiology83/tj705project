package kr.co.tj.recordservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {
	private Long boardId; 
	private String buyer; 
	private String seller;
	private Boolean hasChat;
}
