package kr.co.tj.userservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.userservice.dto.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	UserEntity findByUsername(String username);

}
