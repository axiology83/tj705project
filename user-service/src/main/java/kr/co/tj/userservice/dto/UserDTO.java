package kr.co.tj.userservice.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String username;

	private String name;

	private String password;

	private Date createAt;

	private Date updateAt;

	private String token;
	
	private UserRole role;

	public UserDTO toUserDTO(UserEntity userEntity) {
		this.username = userEntity.getUsername();
		this.name = userEntity.getName();
		this.role = userEntity.getRole();
		this.createAt = userEntity.getCreateAt();
		this.updateAt = userEntity.getUpdateAt();
		this.token = userEntity.getToken();
		return this;
	}

	public UserEntity toUserEntity() {
		return UserEntity.builder()
				.username(username)
				.password(password)
				.name(name)
				.role(UserRole.TYPE1)
				.createAt(createAt)
				.updateAt(updateAt)
				.build();
	}

	public static UserDTO toUserDTO(UserRequest ureq) {
		return UserDTO.builder()
				.username(ureq.getUsername())
				.password(ureq.getPassword())
				.name(ureq.getName())
				.role(ureq.getRole())
				.build();
	}

	public UserResponse toUserResponse() {
		return UserResponse.builder()
				.username(username)
				.name(name)
				.createAt(createAt)
				.updateAt(updateAt)
				.token(token)
				.role(role)
				.build();
	}

	public static UserDTO toUserDTO(UserLoginRequest userLoginRequest) {
		return UserDTO.builder()
				.username(userLoginRequest.getUsername())
				.password(userLoginRequest.getPassword())
				.build();
	}

}
