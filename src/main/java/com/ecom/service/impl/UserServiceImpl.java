package com.ecom.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.UserDto;
import com.ecom.dto.UserRole;
import com.ecom.entity.User;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.exception.UsernameAlreadyExistsException;
import com.ecom.repo.UserRepo;
import com.ecom.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	ModelMapper modelMapper;

	public List<UserDto> fetchAllUsers() {
		List<User> users = userRepo.findAll();
		return users.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
	}

	@Override
	public UserDto addUser(UserDto user) {
		User users = modelMapper.map(user, User.class);
		if (userRepo.existsByUsername(users.getUsername())) {
			throw new UsernameAlreadyExistsException("User", "username", users.getUsername());
		}
		if (users.getRole() == null) {
			users.setRole(UserRole.CUSTOMER);
		}
		User newUser = userRepo.save(users);
		return modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public User fetchUserById(Long id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id)));
		return user;
	}

	@Override
	public UserDto updateUser(UserDto user, Long id) {
		User existingUser = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id)));
		User update = modelMapper.map(existingUser, User.class);
		User updatedUser = modelMapper.map(user, User.class);
		update.setFirstname(updatedUser.getFirstname());
		update.setLastname(updatedUser.getLastname());
		update.setRole(updatedUser.getRole());
		if (updatedUser.getAddress() != null) {
			update.setAddress(updatedUser.getAddress());
		}
		update.setEmail(updatedUser.getEmail());
		update.setPhone(updatedUser.getPhone());
		log.info("Updating user with id: {}", update.getFirstname());
		userRepo.saveAndFlush(update);
		return modelMapper.map(update, UserDto.class);
	}
}
