package kr.co.tj.userservice.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.tj.userservice.dto.OrderResponse;
import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserEntity;
import kr.co.tj.userservice.dto.UserRequest;
import kr.co.tj.userservice.feign.OrderFeign;
import kr.co.tj.userservice.jpa.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderFeign orderFeign;

	// 주문목록 불러오기(임의)
	public UserDTO getOrders(String username) {

		UserEntity userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {
			throw new RuntimeException("존재하지 않는 사용입니다.");
		}

		UserDTO userDTO = new UserDTO();
		userDTO = userDTO.toUserDTO(userEntity);

		// 서비스간의 통신: feign 이용해서 통신한 코드
		List<OrderResponse> orderList = orderFeign.getOrdersByUsername(username);

		userDTO.setOrderList(orderList);

		return userDTO;
	}

	// 회원가입
	public UserDTO createUser(UserDTO userDTO) {
		userDTO = getDate(userDTO);

		UserEntity userEntity = userDTO.toUserEntity();

		userEntity = userRepository.save(userEntity);

		return userDTO.toUserDTO(userEntity);
	}

	// 수정
	@Transactional
	public UserRequest updateUser(String username, UserRequest userRequest) {
		UserEntity userEntity = userRepository.findByUsername(username);

		// 입력한 기존 비밀번호(orgPassword)와 저장된 비밀번호(userEntity.getPassword())를 비교합니다
		if (userRequest.getOrgPassword().equals(userEntity.getPassword())) {
			// 새로운 비밀번호와 비밀번호 확인 값이 일치하는지 확인합니다
			if (userRequest.getPassword().equals(userRequest.getPassword2())) {
				userEntity.setName(userRequest.getName());
				userEntity.setPassword(userRequest.getPassword());
				userRepository.save(userEntity);
				return userRequest;
			} else {
				// 비밀번호와 비밀번호 확인 값이 일치하지 않을 경우 예외 처리 또는 오류 메시지를 반환할 수 있습니다.
				throw new IllegalArgumentException("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
			}
		} else {
			// 기존 비밀번호가 올바르지 않을 경우 예외 처리 또는 오류 메시지를 반환할 수 있습니다.
			throw new IllegalArgumentException("기존 비밀번호가 올바르지 않습니다.");
		}
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

	// 삭제
	@Transactional
	public void deleteUser(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);

		userRepository.delete(userEntity);
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
		UserEntity userEntity = userRepository.findByUsername(username);

		return new UserDTO().toUserDTO(userEntity);
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
