package kr.co.tj.chatservice.room.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String>{

	Optional<ChatRoomEntity> findByRoomId(String roomId);

}
