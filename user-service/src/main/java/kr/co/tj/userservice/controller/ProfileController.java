package kr.co.tj.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserResponse;
import kr.co.tj.userservice.service.ProfileService;
import kr.co.tj.userservice.service.UserService;

@RestController
@RequestMapping("/profile-service")
public class ProfileController {

	private Environment env;

	private UserService userService;

	private ProfileService profileService;

	@Autowired
	public ProfileController(Environment env, UserService userService, ProfileService profileService) {
		super();
		this.env = env;
		this.userService = userService;
		this.profileService = profileService;
	}

	// 정보 상세보기(이름, 닉네임, 가입일, 갱신일 - 공개)
	@GetMapping("/users/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		UserDTO userDTO = userService.getUser(username);
		UserResponse userResponse = userDTO.toUserResponse();

		return ResponseEntity.status(HttpStatus.OK).body(userResponse);
	}

	// username의 게시글 가져오기(board, review, qna - 공개)
	@GetMapping("/user/{username}/posts")
	public ResponseEntity<?> getBoards(@PathVariable("username") String username) {
		UserDTO userDTO = profileService.getAllPosts(username);

		UserResponse userResponse = userDTO.toUserResponse();

		return ResponseEntity.status(HttpStatus.OK).body(userResponse);
	}

	// 테스트용
	@GetMapping("/health_check")
	public String status() {
		return "profile service입니다" + env.getProperty("local.server.port");
	}

}
