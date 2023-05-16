package kr.co.tj.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserEntity;
import kr.co.tj.userservice.jpa.UserRepository;

@Service
public class ProfileService {
	
	private UserRepository userRepository;

	@Autowired
	public ProfileService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			TokenProvider tokenProvider) {
		super();
		this.userRepository = userRepository;
	}

	

	// 비밀번호 가져오기
	public String getUserPassword(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity != null) {
			return userEntity.getPassword();
		} else {
			throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
		}
	}

	// 상세보기
	@Transactional
	public UserDTO getUser(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);

		return new UserDTO().toUserDTO(userEntity);
	}

}
