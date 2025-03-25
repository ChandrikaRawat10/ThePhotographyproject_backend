package com.example.photographyProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.photographyProject.model.User;
import com.example.photographyProject.service.UserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class AuthController {
	
	@Autowired
	private UserDetailsService userService;
	
	@PostMapping("/signup")
	public String signUp( @RequestBody User user) {
		return userService.registerUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		return userService.authenticateUser(username, password);
	}
}
