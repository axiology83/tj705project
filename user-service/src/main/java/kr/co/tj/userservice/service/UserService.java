package kr.co.tj.userservice.service;

import java.util.List;

import kr.co.tj.userservice.dto.UserDTO;

public interface UserService {
	
	UserDTO login(UserDTO userDTO);
	List<UserDTO> getUsers();
	UserDTO getUser(String username);
	UserDTO createUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO);
	void deleteUser(String username);
	UserDTO setDate(UserDTO userDTO);
	
	void testinsert(UserDTO userDTO);
	

}
