package kr.co.tj.userservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.tj.boardservice.dto.BoardResponse;

@FeignClient(name = "board-service")
public interface BoardFeign {
	
	@GetMapping("/board-service/boards/user/{username}")
	public List<BoardResponse> getBoardsByUsername(@PathVariable() String username);
	
}
