package kr.co.tj.userservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserRequest;
import kr.co.tj.userservice.dto.UserResponse;
import kr.co.tj.userservice.service.UserService;

@RestController
@RequestMapping("/user-service")
public class UserController {

	
	private Environment env;
	private UserService userService;
	
	@Autowired
	public UserController(Environment env, UserService userService) {
		this.env = env;
		this.userService = userService;
	}

	

	// 회원가입
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
		UserDTO userDTO = UserDTO.toUserDTO(userRequest);

		userDTO = userService.createUser(userDTO);
		UserResponse userResponse = userDTO.toUserResponse();

		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
	}

	// 사용자 목록
	@GetMapping("/users")
	public ResponseEntity<?> getUsers() {
		List<UserDTO> userDTOs = userService.getUsers();
		List<UserResponse> userResponses = userDTOs.stream().map(UserDTO::toUserResponse).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(userResponses);
	}

	// 정보 상세보기
	@GetMapping("/users/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		UserDTO userDTO = userService.getUser(username);
		UserResponse userResponse = userDTO.toUserResponse();

		return ResponseEntity.status(HttpStatus.OK).body(userResponse);
	}

	// 수정
//	@PutMapping("/users")
//	public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
//		String org
//	
//			if (userRequest.getOrgPassword() == null
//					|| !userRequest.getOrgPassword().equals(userService.getUser(userRequest.getUsername()).getPassword())) {
//				throw new IllegalArgumentException("기존 비밀번호가 올바르지 않습니다.");
//			}
//
//			if (!userRequest.getPassword().equals(userRequest.getPassword2())) {
//				throw new IllegalArgumentException("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
//			}
//
//			userRequest = userService.updateUser(userRequest);
//			return ResponseEntity.status(HttpStatus.OK).body(userRequest);
//			
//		
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		
//	}

	// 삭제
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
		userService.deleteUser(username);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// 테스트용
	@GetMapping("/health_check")
	public String status() {
		return "user service입니다" + env.getProperty("local.server.port");
	}
	
	
	
	@PostMapping("/testinsert")
	public void testinsert() {
		
	}

}
