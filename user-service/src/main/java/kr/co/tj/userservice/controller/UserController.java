package kr.co.tj.userservice.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import kr.co.tj.userservice.dto.UserLoginRequest;
import kr.co.tj.userservice.dto.UserRequest;
import kr.co.tj.userservice.dto.UserResponse;
import kr.co.tj.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class UserController {

	
	private Environment env;
	private UserService userService;
	
	@Autowired
	public UserController(Environment env, UserService userService) {
		super();
		this.env = env;
		this.userService = userService;
	}



	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){
		Map<String, Object> map = new HashMap<>();
		
		if(userLoginRequest.getUsername() == null || userLoginRequest.getUsername().isEmpty()) {
			map.put("result", "id를 똑바로 입력하세요");
			return ResponseEntity.ok().body(map);
		}
		
		if(userLoginRequest.getPassword() == null || userLoginRequest.getPassword().isEmpty()) {
			map.put("result", "password를 똑바로 입력하세요");
			return ResponseEntity.ok().body(map);
		}
		
		UserDTO userDTO = UserDTO.toUserDTO(userLoginRequest);
		
		userDTO = userService.login(userDTO);
		
		if(userDTO == null) {
			map.put("result", "사용자명이나 비밀번호가 잘못되었습니다.");
			return ResponseEntity.ok().body(map);
		}
		
		UserResponse userResponse = userDTO.toUserResponse();
		map.put("result", userResponse);
		return ResponseEntity.ok().body(map);
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
		//List<UserResponse> userResponses = userDTOs.stream().map(UserDTO::toUserResponse).collect(Collectors.toList());
		List<UserResponse> userResponses = new ArrayList<>();
		
		for(UserDTO x : userDTOs) {
			userResponses.add(x.toUserResponse());
		}

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
	@PutMapping("/users")
	public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
		String orgPassword = userRequest.getOrgPassword();
		String dbPassword = userService.getUser(userRequest.getUsername()).getPassword();
	
			if (orgPassword == null || !orgPassword.equals(dbPassword)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호를 확인하세요");
			}

			if (!userRequest.getPassword().equals(userRequest.getPassword2())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호를 확인하세요");
			}
			
			UserDTO userDTO = UserDTO.toUserDTO(userRequest);

			userDTO = userService.updateUser(userDTO);
			UserResponse userResponse = userDTO.toUserResponse();
			
			
			return ResponseEntity.status(HttpStatus.OK).body(userResponse);
			
		
		
		
	}

	// 삭제
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteUser(@RequestBody UserRequest userRequest) {
		
		UserDTO orgDTO = userService.getUser(userRequest.getUsername());
		if(!orgDTO.getPassword().equals(userRequest.getPassword())){
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비번 확인하세요");
		}
		
		userService.deleteUser(orgDTO.getUsername());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// 테스트용
	@GetMapping("/health_check")
	public String status() {
		log.info("data.world: {}", env.getProperty("data.world"));
		log.info("data.test: {}", env.getProperty("data.test"));
		
		
		return "user service입니다" + env.getProperty("local.server.port");
	}
	
	
	@GetMapping("/test")
	public ResponseEntity<?> test(){
		System.out.println(":::::::::::::::잘 될까?::::::로그인 하고 토큰 첨부해야 되는데:::::::::::::");
		
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse());
	}
	
	@PostMapping("/testinsert")
	public void testinsert() {
		System.out.println("테스트용 데이터를 여러개 주입합니다.");
		System.out.println("테스트용 데이터를 여러개 주입합니다.");
		System.out.println("테스트용 데이터를 여러개 주입합니다.");
		System.out.println("테스트용 데이터를 여러개 주입합니다.");
		System.out.println("테스트용 데이터를 여러개 주입합니다.");
		
		Random rand = new Random();
		for (int i = 1; i < 101; i++) {
			
			String idnum = String.format("%03d", i);
			int year = rand.nextInt(3) + 2021;
			int month = rand.nextInt(12) + 1;
			int day = rand.nextInt(28) + 1;
			Calendar cal = Calendar.getInstance();
			cal.set(year, month-1, day);
			Date date = cal.getTime();
			
			UserDTO dto = UserDTO.builder()
					.username("m" + idnum)
					.name(idnum + "번째 장원영")
					.password("1")
					.createAt(date)
					.updateAt(date)
					.build();
			
			userService.testinsert(dto);
			
		}
				
	}

}
