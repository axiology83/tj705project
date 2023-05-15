package kr.co.tj.chatservice.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDTO{
	
	
	
	public enum MessageType {
		ENTER, TALK
	}
	
	private MessageType type;
	private String roomId;
	private String sender;
	private String talk;

}
