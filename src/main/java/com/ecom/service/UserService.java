package com.ecom.service;

import java.util.List;

import com.ecom.dto.UserDto;
import com.ecom.entity.User;

public interface UserService {
	public List<UserDto> fetchAllUsers();

	public UserDto addUser(UserDto user);

	public User fetchUserById(Long id);

	public UserDto updateUser(UserDto user, Long id);
}
