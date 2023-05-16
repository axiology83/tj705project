package kr.co.tj.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.tj.boardservice.dto.BoardResponse;
import kr.co.tj.reviewservice.dto.ReviewResponse;
import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserEntity;
import kr.co.tj.userservice.feign.BoardFeign;
import kr.co.tj.userservice.feign.QnAFeign;
import kr.co.tj.userservice.feign.ReviewFeign;
import kr.co.tj.userservice.jpa.UserRepository;

@Service
public class ProfileService {
	
	private UserRepository userRepository;
	
	private BoardFeign boardFeign;
	private ReviewFeign reviewFeign;
//	private QnAFeign qnaFeign;
	
	@Autowired
	public ProfileService(UserRepository userRepository, BoardFeign boardFeign, ReviewFeign reviewFeign, QnAFeign qnaFeign) {
		super();
		this.userRepository = userRepository;
		this.boardFeign = boardFeign;
		this.reviewFeign = reviewFeign;
//		this.qnaFeign = qnaFeign;
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

	// 게시글 불러오기
	public UserDTO getAllPosts(String username) {

		UserEntity userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {
			throw new RuntimeException("존재하지 않는 사용자입니다.");
		}

		UserDTO userDTO = new UserDTO();
		userDTO = userDTO.toUserDTO(userEntity);

		// 서비스간의 통신: feign 이용해서 통신한 코드
		List<BoardResponse> boardList = boardFeign.getBoardsByUsername(username);
		List<ReviewResponse> reviewList = reviewFeign.getReviewsByUsername(username);
//		List<QnAResponse> qnaList = qnaFeign.getQnAsByUsername(username);
				
		userDTO.setBoardList(boardList);
		userDTO.setReviewList(reviewList);
//		userDTO.setQnAList(qnaList);

		return userDTO;
	}

}
