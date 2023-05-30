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
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String name;

	private Date createAt;

	private Date updateAt;
	
	private String token;
	
	private UserRole role;

}
