package kr.co.tj.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.boardservice.dto.BoardEntity;
import kr.co.tj.boardservice.service.BoardService;
import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserResponse;
import kr.co.tj.userservice.service.UserService;

@RestController
@RequestMapping("/profile-service")
public class ProfileController {

	private Environment env;

	private UserService userService;

	private BoardService boardService;

	@Autowired
	public ProfileController(Environment env, BCryptPasswordEncoder passwordEncoder, UserService userService,
			BoardService boardService) {
		super();
		this.env = env;
		this.userService = userService;
		this.boardService = boardService;
	}

	// 정보 상세보기(이름, 닉네임, 가입일, 갱신일)
	@GetMapping("/users/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		UserDTO userDTO = userService.getUser(username);
		UserResponse userResponse = userDTO.toUserResponse();

		return ResponseEntity.status(HttpStatus.OK).body(userResponse);
	}

	// 게시글 보기
	@GetMapping("/boards/users/{username}")
	public ResponseEntity<List<BoardEntity>> getUserBoards(@PathVariable("username") String username) {
		// Board 서비스에서 게시글을 받아옴
		List<BoardEntity> boards = boardService.getBoardsByUser(username);
		// 게시글을 응답으로 반환
		return ResponseEntity.ok(boards);
	}

	// 테스트용
	@GetMapping("/health_check")
	public String status() {
		return "user service입니다" + env.getProperty("local.server.port");
	}

}
