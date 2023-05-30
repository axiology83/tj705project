package kr.co.tj.userservice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.tj.userservice.DataNotFoundException;
import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserEntity;
import kr.co.tj.userservice.dto.UserRequest;
import kr.co.tj.userservice.dto.UserRole;
import kr.co.tj.userservice.dto.UserRoleRequest;
import kr.co.tj.userservice.jpa.UserRepository;

@Service
public class UserServiceImpl implements UserService {

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
		// username으로 db에서 user의 entity찾기
		UserEntity userEntity = userRepository.findByUsername(userDTO.getUsername());

		// 사용자(user) entity가 없으면 null 반환
		if (userEntity == null) {
			return null;
		}

		// 입력한 비밀번호와 저장된 비밀번호가 일치하지 않으면 null 반환
		if (!passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword())) {
			return null;
		}

		// 토큰 생성
		String token = tokenProvider.create(userEntity);

		// 사용자 정보를 UserDTO로 변환
		userDTO = userDTO.toUserDTO(userEntity);
		// 토큰, role, 비번의 필드 설정
		userDTO.setToken(token);
		userDTO.setRole(userEntity.getRole());
		userDTO.setPassword("");

		// 인증된 사용자 정보가 포함된 UserDTO반환
		return userDTO;
	}

	// 회원가입
	public UserDTO createUser(UserDTO userDTO) {
		userDTO = getDate(userDTO);

		// UserDTO -> UserEntity로 변환
		UserEntity userEntity = userDTO.toUserEntity();

		// 비밀번호 암호화하여 저장
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

		// Role 설정
		if (userEntity.getUsername().equals("m1000")) {
			userEntity.setRole(UserRole.TYPE2); // 회원 m1000의 경우 admin 권한 부여 (임의)
		} else if (userEntity.getUsername().equals("b001")) {
			userEntity.setRole(UserRole.TYPE3); // 회원 b001의 경우 TYPE3 권한 설정 (임의)
		} else {
			userEntity.setRole(UserRole.TYPE1); // 기본적으로 TYPE1 권한 설정
		}

		// 사용자 Entity 저장
		userEntity = userRepository.save(userEntity);

		// UserDTO로 변환된 사용자 Entity 반환
		return userDTO.toUserDTO(userEntity);
	}

	// 수정
	@Transactional
	public UserRequest updateUser(String username, UserRequest userRequest) {
		// 주어진 사용자명으로 db에서 사용자 Entity 찾기
		UserEntity userEntity = userRepository.findByUsername(username);

		// 입력한 기존 비밀번호(orgPassword)와 저장된 비밀번호(userEntity.getPassword())를 비교
		if (passwordEncoder.matches(userRequest.getOrgPassword(), userEntity.getPassword())) {
			// 새로운 비밀번호와 비밀번호 확인 값이 일치하는지 확인
			if (userRequest.getPassword().equals(userRequest.getPassword2())) {

				// 새 비밀번호 암호화하여 저장
				String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
				userEntity.setPassword(encodedPassword);
			} else {
				// 비밀번호와 비밀번호 확인 값이 일치하지 않을 경우 예외 처리
				throw new DataNotFoundException("오류발생. 다시 시도해주세요.");
			}
			// 이름 변경
			userEntity.setName(userRequest.getName());

			// 사용자 Entity 저장
			userRepository.save(userEntity);
			return userRequest;

		} else {
			// 기존 비밀번호가 올바르지 않을 경우 예외 처리
			throw new DataNotFoundException("오류발생. 다시 시도해주세요.");
		}
	}

	// 권한 수정
	@Transactional
	public void updateUserRole(String username, UserRoleRequest roleRequest) {
		// 주어진 사용자명으로 db에서 사용자 Entity 찾기
		UserEntity userEntity = userRepository.findByUsername(username);

		// 권한 변경
		userEntity.setRole(roleRequest.getRole());

		// 사용자 Entity 저장
		userRepository.save(userEntity);
	}

	// 비밀번호 가져오기
	public String getUserPassword(String username) {
		// 주어진 사용자명으로 db에서 사용자 Entity 찾기
		UserEntity userEntity = userRepository.findByUsername(username);
		// 사용자 Entity가 존재하면 비밀번호 반환
		if (userEntity != null) {
			return userEntity.getPassword();
			// 그렇지 않으면 예외 처리
		} else {
			throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
		}
	}

	// 삭제
	@Transactional
	public void deleteUser(String username) {
		// 주어진 사용자명으로 db에서 사용자 Entity 찾기
		UserEntity userEntity = userRepository.findByUsername(username);
		// 사용자 Entity가 존재하지 않으면 예외 처리
		if (userEntity == null) {
			throw new DataNotFoundException("사용자를 찾을 수 없습니다");
		}
		// 사용자 Entity 삭제
		userRepository.delete(userEntity);
	}

	// 목록
	@Transactional
	public List<UserDTO> getUsers() {
		// 모든 사용자 Entity를 db에서 조회
		List<UserEntity> userEntities = userRepository.findAll();
		// 각 게시판의 목록을 하나의 스트림으로 변환(각각의 entity -> dto로 변환)
		return userEntities.stream().map(userEntity -> new UserDTO().toUserDTO(userEntity))
				// 변환된 스트림을 하나의 리스트로 모아서 반환
				.collect(Collectors.toList());
	}

	// 목록 검색
	public Page<UserDTO> findAll(int pageNum, String st_startDate, String st_endDate) {

		Date startDate;
		Date endDate;

		try {
			// 날짜 포맷 UTC 시간대로 설정
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

			// 입력받은 날짜 문자열을 Date 객체로 변환
			startDate = formatter.parse(st_startDate);
			endDate = formatter.parse(st_endDate);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("시간 정보에 문제가 있습니다");
		}

		// 정렬 설정을 위한 Sort.Order 객체 리스트 생성
		List<Sort.Order> sortList = new ArrayList<>();
		sortList.add(Sort.Order.desc("createAt")); // 가입일 내림차순 정렬(최근 가입한 사람이 맨 위에 보임)

		// 페이징 정보 생성
		Pageable pageable = PageRequest.of(pageNum, 20, Sort.by(sortList));

		// 날짜 범위와 페이징 정보를 사용하여 사용자 목록 조회
		Page<UserEntity> page_member = userRepository.findByCreateAtBetween(startDate, endDate, pageable);

		// 조회된 UserEntity 객체를 UserDTO로 변환하여 새로운 Page 객체 생성
		Page<UserDTO> page_dto = page_member.map(userEntity -> new UserDTO(null, userEntity.getUsername(),
				userEntity.getName(), null, userEntity.getCreateAt(), userEntity.getUpdateAt(), userEntity.getToken(),
				userEntity.getRole()));

		// 변환된 Page 객체 반환
		return page_dto;
	}

	// 목록 페이징
	@Transactional
	public Page<UserDTO> getPagingUsers(Pageable pageable) {
		// 페이지네이션을 적용해 사용자 entity를 db에서 조회
		Page<UserEntity> userEntities = userRepository.findAll(pageable);

		// 각 사용자 entity를 UserDTO로 변환, 페이지네이션 정보와 함께 반환
		return userEntities.map(userEntity -> new UserDTO().toUserDTO(userEntity));
	}

	// 상세보기
	@Transactional
	public UserDTO getUser(String username) {
		// 주어진 사용자명으로 db에서 사용자 entity 찾기
		UserEntity userEntity = userRepository.findByUsername(username);
		// 사용자 entity가 존재하지 않을경우 예외 처리
		if (userEntity == null) {
			throw new DataNotFoundException("사용자를 찾을 수 없습니다");
		}
		// 사용자 entity를 userDTO로 변환하여 반환
		return new UserDTO().toUserDTO(userEntity);
	}

	// 날짜정보 주입
	public UserDTO getDate(UserDTO userDTO) {
		Date date = new Date();

		// createAt 필드가 null일 경우 createAt과 updateAt필드를 현재날짜로 설정
		if (userDTO.getCreateAt() == null) {
			userDTO.setCreateAt(date);
		}
		userDTO.setUpdateAt(date);

		// 변경된 UserDTO 반환
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
