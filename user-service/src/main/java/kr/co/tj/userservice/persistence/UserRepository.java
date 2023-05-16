package kr.co.tj.userservice.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	Optional<UserEntity> findByUsername(String username);

}
