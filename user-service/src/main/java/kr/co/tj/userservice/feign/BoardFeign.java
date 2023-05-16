package kr.co.tj.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.tj.boardservice.dto.BoardEntity;

@FeignClient(name = "board-service")
public interface BoardFeign {
	
	@GetMapping("/boards/user/{username}")
    BoardEntity[] getBoardsByUser(@PathVariable("username") String username);
	
}
