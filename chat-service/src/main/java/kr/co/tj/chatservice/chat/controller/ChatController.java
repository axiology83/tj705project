package kr.co.tj.chatservice.chat.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import kr.co.tj.chatservice.chat.dto.ChatMessageDTO;






@Controller 
public class ChatController {
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	

	
	
	
	@MessageMapping("/chat/message")
	public void message(ChatMessageDTO dto) {
		
		if(dto.getSender() == null || dto.getSender() == "") {
			
			dto.setSender("손님");
		}
		
	
		if(ChatMessageDTO.MessageType.ENTER.equals(dto.getType())) {
			
			dto.setTalk(dto.getSender() + "님이 입장하였습니다.");
			
			messagingTemplate.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto);
			
		}
		
		else{
			
			messagingTemplate.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto);
							
		}
		
		
	}

}
