package kr.co.tj.chatservice.room.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import kr.co.tj.chatservice.room.dto.ChatRoomDTO;
import kr.co.tj.chatservice.room.dto.ChatRoomRequest;
import kr.co.tj.chatservice.room.service.ChatRoomService;

@RestController
@RequestMapping("/chat-service")
public class ChatRoomController {

	private Environment env;
	private ChatRoomService chatRoomService;
	
	@Autowired
	public ChatRoomController(Environment env, ChatRoomService chatRoomService) {
		super();
		this.env = env;
		this.chatRoomService = chatRoomService;
	}



	// 채팅방 생성
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestHeader("Authorization") String bearerToken, @RequestBody ChatRoomRequest chatRoomRequest) {
				
		// 헤더에서 
		String token = bearerToken.replace("Bearer ", "");
		String str = env.getProperty("data.SECRET_KEY");
		
		String encodedStr = Base64.getEncoder().encodeToString(str.getBytes());
		
		String username1 = Jwts.parser()
				.setSigningKey(encodedStr)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		String username2 = chatRoomRequest.getUsername2();
		
		
		    String[] users = { username1, username2 };
	        Arrays.sort(users);
	        username1 = users[0];
	        username2 = users[1];
	        String title = username1 + "-" + username2;
	        
	        
	        chatRoomRequest.setUsername1(username1);
	        chatRoomRequest.setUsername2(username2);
	        chatRoomRequest.setTitle(title);
		
		
		
		ChatRoomDTO dto = ChatRoomDTO.builder()
				.title(title)
				.username1(username1)
				.username2(username2)
				.build();
		
		chatRoomService.insertRoom(dto);
		
		

		return ResponseEntity.ok().body("채팅방이 생성되었슴돠");
	}

	

//	@GetMapping("/list")
//	public ResponseEntity<?> findChatrooms() {
//		Map<String, Object> map = new HashMap<>();
//
//		List<ChatRoomDTO> dtolist = chatRoomService.findAll();
//
//		map.put("result", dtolist);
//
//		return ResponseEntity.ok().body(map);
//	}
//
//	@GetMapping("/room/{roomId}")
//	public ResponseEntity<?> findRoom(@PathVariable String roomId) {
//		Map<String, Object> map = new HashMap<>();
//		ChatRoomDTO dto = chatRoomService.findRoom(roomId);
//		map.put("result", dto);
//
//		return ResponseEntity.ok().body(map);
//	}

}
