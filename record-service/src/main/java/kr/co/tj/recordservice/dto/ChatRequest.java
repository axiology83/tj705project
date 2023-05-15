package kr.co.tj.recordservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {
	private Long boardId; 
	private String buyer; 
	private Boolean hasChat;
}
