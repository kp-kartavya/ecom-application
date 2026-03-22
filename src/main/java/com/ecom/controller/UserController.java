package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.UserRequest;
import com.ecom.dto.UserResponse;
import com.ecom.entity.User;
import com.ecom.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserResponse> users = userService.fetchAllUsers();
		return ResponseEntity.ok(users);
	}

	@PostMapping("/addUser")
	public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest user) {
		UserResponse newUser = userService.addUser(user);
		return ResponseEntity.ok(newUser);
	}

	@GetMapping("/getUserById")
	public ResponseEntity<User> getUserById(@RequestParam Long id) {
		User user = userService.fetchUserById(id);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user, @RequestParam Long id) {
		UserResponse updateUser = userService.updateUser(user, id);
		return ResponseEntity.ok(updateUser);
	}

}
