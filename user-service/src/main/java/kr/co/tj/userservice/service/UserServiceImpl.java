package kr.co.tj.userservice.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.tj.userservice.DataNotFoundException;
import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserEntity;
import kr.co.tj.userservice.dto.UserRequest;
import kr.co.tj.userservice.dto.UserRole;
import kr.co.tj.userservice.jpa.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder;

	private TokenProvider tokenProvider;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			TokenProvider tokenProvider) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}

	// 로그인
	public UserDTO login(UserDTO userDTO) {
		UserEntity userEntity = userRepository.findByUsername(userDTO.getUsername());

		if (userEntity == null) {
			return null;
		}

		if (!passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword())) {
			return null;
		}

		String token = tokenProvider.create(userEntity);

		userDTO = userDTO.toUserDTO(userEntity);
		userDTO.setToken(token);
		userDTO.setRole(userEntity.getRole());
		userDTO.setPassword("");

		return userDTO;
	}

	// 회원가입
	public UserDTO createUser(UserDTO userDTO) {
		userDTO = getDate(userDTO);

		UserEntity userEntity = userDTO.toUserEntity();

		// 비밀번호 암호화
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		
		 // Role 설정
	    if (userEntity.getUsername().equals("m1000")) {
	        userEntity.setRole(UserRole.TYPE2); // 회원 m1000의 경우 admin 권한 부여
	    } else {
	        userEntity.setRole(UserRole.TYPE1); // 기본적으로 TYPE1 권한 설정
	    }

		userEntity = userRepository.save(userEntity);

		return userDTO.toUserDTO(userEntity);
	}

	// 수정
	@Transactional
	public UserRequest updateUser(String username, UserRequest userRequest) {
		UserEntity userEntity = userRepository.findByUsername(username);

		// 입력한 기존 비밀번호(orgPassword)와 저장된 비밀번호(userEntity.getPassword())를 비교
		if (passwordEncoder.matches(userRequest.getOrgPassword(), userEntity.getPassword())) {
			// 새로운 비밀번호와 비밀번호 확인 값이 일치하는지 확인
			if (userRequest.getPassword().equals(userRequest.getPassword2())) {

				// 비밀번호 암호화
				String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
				userEntity.setPassword(encodedPassword);
			} else {
				// 비밀번호와 비밀번호 확인 값이 일치하지 않을 경우 예외 처리 또는 오류 메시지를 반환
				throw new DataNotFoundException("오류발생. 다시 시도해주세요.");
			}
			// 이름 변경
			userEntity.setName(userRequest.getName());

			userRepository.save(userEntity);
			return userRequest;

		} else {
			// 기존 비밀번호가 올바르지 않을 경우 예외 처리 또는 오류 메시지를 반환
			throw new DataNotFoundException("오류발생. 다시 시도해주세요.");
		}
	}

	// 비밀번호 가져오기
	public String getUserPassword(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity != null) {
			return userEntity.getPassword();
		} else {
			throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
		}
	}

	// 삭제
	@Transactional
	public void deleteUser(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity == null) {
			throw new DataNotFoundException("사용자를 찾을 수 없습니다");
		}
		userRepository.delete(userEntity);
	}

	// 목록
	@Transactional
	public List<UserDTO> getUsers() {
		List<UserEntity> userEntities = userRepository.findAll();
		// 각 게시판의 목록을 하나의 스트림으로 변환
		// Collection::stream - 각 게시판의 목록을 스트림으로 변환하는 함수
		return userEntities.stream().map(userEntity -> new UserDTO().toUserDTO(userEntity))
				// 변환된 스트림을 하나의 리스트로 모음
				.collect(Collectors.toList());
	}

	// 상세보기
	@Transactional
	public UserDTO getUser(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity == null) {
			throw new DataNotFoundException("사용자를 찾을 수 없습니다");
		}
		return new UserDTO().toUserDTO(userEntity);
	}

	// 날짜정보 주입
	public UserDTO getDate(UserDTO userDTO) {
		Date date = new Date();

		if (userDTO.getCreateAt() == null) {
			userDTO.setCreateAt(date);
		}

		userDTO.setUpdateAt(date);
		return userDTO;
	}

	// 테스트 반복문
	public void testinsert(UserDTO dto) {

		String encPassword = passwordEncoder.encode(dto.getPassword());
		dto.setPassword(encPassword);

		UserEntity userEntity = dto.toUserEntity();
		userEntity = userRepository.save(userEntity);

	}

}
