package kr.co.tj.chatservice.room.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.chatservice.DataNotFoundException;
import kr.co.tj.chatservice.room.dto.ChatRoomDTO;
import kr.co.tj.chatservice.room.persistence.ChatRoomEntity;
import kr.co.tj.chatservice.room.persistence.ChatRoomRepository;

@Service
public class ChatRoomServiceImpl {

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	public ChatRoomDTO insert(ChatRoomDTO dto) {
		
		ChatRoomEntity entity = ChatRoomEntity.builder()
				.roomId(UUID.randomUUID().toString())
				.title(dto.getTitle())
			
				.build();
		
		entity = chatRoomRepository.save(entity);
		
		dto.setId(entity.getId());
		dto.setRoomId(entity.getRoomId());
		dto.setTitle(entity.getTitle());
		
		return dto;
		
	}

	public List<ChatRoomDTO> findAll() {
		
		List<ChatRoomEntity> entitylist = chatRoomRepository.findAll();
		List<ChatRoomDTO> dtolist = new ArrayList<>(); 
		entitylist.forEach(entity ->
		dtolist.add(new ChatRoomDTO(
				entity.getId(),
				entity.getRoomId(),
				entity.getTitle()
				
				)
				)
		);
		System.out.println(dtolist);
		return dtolist;
		
	}

	public ChatRoomDTO findRoom(String roomId) {
		Optional<ChatRoomEntity> optional = chatRoomRepository.findByRoomId(roomId);
		if(!optional.isPresent()) {
			throw new DataNotFoundException("채팅방이 없습니다.");
		}
		ChatRoomEntity entity = optional.get();
		ChatRoomDTO dto = new ChatRoomDTO(entity.getId(), entity.getRoomId(), entity.getTitle());
				
		return dto;
	}
	

}