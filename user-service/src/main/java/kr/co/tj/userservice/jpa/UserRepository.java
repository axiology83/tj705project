package kr.co.tj.userservice.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	Optional<UserEntity> findByUsername(String username);

}
