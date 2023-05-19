package kr.co.tj.userservice.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import kr.co.tj.userservice.service.UserServiceImpl;

@RestController
@RequestMapping("/user-service")
public class UserController {

	private Environment env;

	private BCryptPasswordEncoder passwordEncoder;

	private UserServiceImpl userService;

	@Autowired
	public UserController(Environment env, BCryptPasswordEncoder passwordEncoder, UserServiceImpl userService) {
		super();
		this.env = env;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
		Map<String, Object> map = new HashMap<>();

		if (userLoginRequest.getUsername() == null || userLoginRequest.getUsername().isEmpty()) {
			map.put("result", "오류발생. 다시 시도해주세요.");
			return ResponseEntity.ok().body(map);
		}

		if (userLoginRequest.getPassword() == null || userLoginRequest.getPassword().isEmpty()) {
			map.put("result", "오류발생. 다시 시도해주세요.");
			return ResponseEntity.ok().body(map);
		}

		UserDTO userDTO = UserDTO.toUserDTO(userLoginRequest);

		userDTO = userService.login(userDTO);

		if (userDTO == null) {
			map.put("result", "오류발생. 다시 시도해주세요.");
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
	@PutMapping("/users/{username}")
	public ResponseEntity<?> updateUser(@PathVariable("username") String username,
			@RequestBody UserRequest userRequest) {
		try {
			// 로그인한 사용자와 수정하려는 회원이 같은지 확인
			if (!userRequest.getUsername().equals(username)) {
				throw new RuntimeException("접근 권한이 없습니다.");
			}
			// 기존 비밀번호와 입력한 비밀번호 비교
			if (!passwordEncoder.matches(userRequest.getOrgPassword(), userService.getUserPassword(username))) {
				throw new RuntimeException("오류발생. 다시 시도해주세요.");
			}
			// 새로 입력한 비밀번호와 비밀번호 확인 값 비교
			if (!userRequest.getPassword().equals(userRequest.getPassword2())) {
				throw new RuntimeException("오류발생. 다시 시도해주세요.");
			}

			userRequest = userService.updateUser(username, userRequest);
			return ResponseEntity.status(HttpStatus.OK).body(userRequest);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

	// 삭제
	@DeleteMapping("/users/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username,
			@RequestBody UserRequest userRequest) {

		try {
			// 로그인한 사용자와 수정하려는 회원이 같은지 확인
			if (!userRequest.getUsername().equals(username)) {
				throw new RuntimeException("접근 권한이 없습니다.");
			}
			// 기존 비밀번호와 입력한 비밀번호 비교
			if (!passwordEncoder.matches(userRequest.getPassword(), userService.getUserPassword(username))) {
				throw new RuntimeException("오류발생. 다시 시도해주세요.");
			}

			userService.deleteUser(username);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

	// 테스트용
	@GetMapping("/health_check")
	public String status() {
		return "user service입니다" + env.getProperty("local.server.port");
	}

	// 테스트 반복문
	@PostMapping("/users/testinsert")
	public void testinsert() {
		Random rand = new Random();
		for (int i = 1; i < 500; i++) {

			String idnum = String.format("%03d", i);
			int year = rand.nextInt(3) + 2021;
			int month = rand.nextInt(12) + 1;
			int day = rand.nextInt(28) + 1;
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day);
			Date date = cal.getTime();

			UserDTO dto = UserDTO.builder().username("m" + idnum).name(idnum + "번째 장원영").password("1").createAt(date)
					.updateAt(date).build();

			userService.testinsert(dto);

		}
	}

}
