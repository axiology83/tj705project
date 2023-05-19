package kr.co.tj.chatservice.room.service;



import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.chatservice.DataNotFoundException;
import kr.co.tj.chatservice.room.dto.ChatRoomDTO;
import kr.co.tj.chatservice.room.persistence.ChatRoomEntity;
import kr.co.tj.chatservice.room.persistence.ChatRoomRepository;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	public ChatRoomDTO insertRoom(ChatRoomDTO dto) {
			
		
		ChatRoomEntity entity = ChatRoomEntity.builder()
				.title(dto.getTitle())	
				.username1(dto.getUsername1())
				.username2(dto.getUsername2())
				.build();
		
		entity = chatRoomRepository.save(entity);
		
		dto.setId(entity.getId());
				
		return dto;
		
	}

	

	public ChatRoomDTO findRoom(String title) {
		Optional<ChatRoomEntity> optional = chatRoomRepository.findByTitle(title);
		
		if(!optional.isPresent()) {
			
			throw new DataNotFoundException("채팅방이 없습니다.");
		}
		
		ChatRoomEntity entity = optional.get();
		ChatRoomDTO dto = new ChatRoomDTO(
				entity.getId(), 
				entity.getTitle(), 
				entity.getUsername1(), 
				entity.getUsername2(), 
				entity.getContent());
				
		return dto;
		
	}
	

}