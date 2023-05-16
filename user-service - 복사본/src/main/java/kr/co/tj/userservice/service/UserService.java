package kr.co.tj.userservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.tj.userservice.DataNotFoundException;
import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.jpa.UserEntity;
import kr.co.tj.userservice.jpa.UserRepository;
import kr.co.tj.userservice.sec.TokenProvider;

@Service
public class UserService {


	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private TokenProvider tokenProvider;
	
	
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			TokenProvider tokenProvider) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}


	public UserDTO login(UserDTO userDTO) {
		Optional<UserEntity> optional = userRepository.findByUsername(userDTO.getUsername());
				
		
		if(!optional.isPresent()) {
			return null;
		} 
		
		UserEntity entity = optional.get();
		
		if(!passwordEncoder.matches(userDTO.getPassword(), entity.getPassword())){
			return null;
		}
		
		
		String token = tokenProvider.create(entity);
		
		userDTO.toUserDTO(entity);
		userDTO.setToken(token);
		userDTO.setPassword("");
		return userDTO;
		
	}

	
	// 회원가입
	@Transactional
	public UserDTO createUser(UserDTO userDTO) {
		userDTO = getDate(userDTO);
		
		String encPassword = passwordEncoder.encode(userDTO.getPassword());
		userDTO.setPassword(encPassword);

		UserEntity userEntity = userDTO.toUserEntity();
		userEntity = userRepository.save(userEntity);

		return userDTO.toUserDTO(userEntity);
	}

	// 수정
	@Transactional
	public UserDTO updateUser(UserDTO userDTO) {
		Date date = new Date();
	
		UserDTO orgDTO = getUser(userDTO.getUsername());		
				
			UserEntity entity = UserEntity.builder()
					.id(orgDTO.getId())
					.username(orgDTO.getUsername())
					.password(userDTO.getPassword())
					.name(userDTO.getName())
					.createAt(orgDTO.getCreateAt())
					.updateAt(date)
					.build();
			
			
			entity = userRepository.save(entity);
			orgDTO.toUserDTO(entity);
			
			return orgDTO;
				
	}


	// 삭제
	@Transactional
	public void deleteUser(String username) {
		Optional<UserEntity> optional = userRepository.findByUsername(username);
		if(!optional.isPresent()) {
			throw new DataNotFoundException("사용자를 찾을 수 없습니다");
		}
		
		UserEntity entity = optional.get();
		userRepository.delete(entity);
		
	}

	// 목록
	@Transactional
	public List<UserDTO> getUsers() {
		List<UserEntity> userEntities = userRepository.findAll();
		return userEntities.stream().map(userEntity -> new UserDTO().toUserDTO(userEntity))
				.collect(Collectors.toList());
	}

	// 상세보기
	@Transactional
	public UserDTO getUser(String username) {
		 Optional<UserEntity> optional = userRepository.findByUsername(username);
		 
		 if(!optional.isPresent()) {
			 throw new DataNotFoundException("사용자 정보를 찾을 수 없음");
		 }
		 
		 UserEntity entity = optional.get();

		return new UserDTO().toUserDTO(entity);
	}

	// 날짜정보 주입
	private UserDTO getDate(UserDTO userDTO) {
		Date date = new Date();

		if (userDTO.getCreateAt() == null) {
			userDTO.setCreateAt(date);
		}

		userDTO.setUpdateAt(date);
		return userDTO;
	}

}
