package kr.co.tj.userservice.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String name;

	private Date createAt;

	private Date updateAt;
	
	private String token;
	
	public enum Role {
		TYPE1("user"), TYPE2("admin"), TYPE3("blocked");

		private String roleName;

		Role(String roleName) {
			this.roleName = roleName;
		}

		public String getRoleName() {
			return roleName;
		}
	}

}
