package com.ecom.service;

import java.util.List;

import com.ecom.dto.UserRequest;
import com.ecom.dto.UserResponse;
import com.ecom.entity.User;

public interface UserService {
	public List<UserResponse> fetchAllUsers();

	public UserResponse addUser(UserRequest user);

	public User fetchUserById(Long id);

	public UserResponse updateUser(UserRequest user, Long id);
}
