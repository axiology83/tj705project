package kr.co.tj.chatservice.room.dto;









import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDTO {

	private int id;
	private String username1;
	private String username2;
	private String content;


}