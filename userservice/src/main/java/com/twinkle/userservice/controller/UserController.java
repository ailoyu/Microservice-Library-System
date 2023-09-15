package com.twinkle.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twinkle.userservice.data.User;
import com.twinkle.userservice.model.UserDTO;
import com.twinkle.userservice.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/listUser")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
	@PostMapping("/addUser")
	public UserDTO addUser(@RequestBody UserDTO user) {
		return userService.saveUser(user);
	}
	@PostMapping("/login")
	public UserDTO loginUser(@RequestBody UserDTO dto) {
		// cần trả về token nên sử dụng UserDTO
		return userService.login(dto.getUsername(), dto.getPassword());
	}
}
