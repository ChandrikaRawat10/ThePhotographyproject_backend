package com.example.photographyProject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.photographyProject.model.Customer;
import com.example.photographyProject.service.UserDetailsService;

import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class AuthController {
	
	@Autowired
	private UserDetailsService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> signUp(@RequestBody Customer user) {
	    String message = userService.registerUser(user);
	    Map<String, String> response = new HashMap<>();
	    response.put("message", message);
	    return ResponseEntity.ok(response);
	}

	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username,
	                                                 @RequestParam("password") String password) {
	    boolean authenticated = userService.authenticateUser(username, password);
	    Map<String, Object> response = new HashMap<>();
	    response.put("success", authenticated);
	    response.put("message", authenticated ? "Login successful" : "Invalid credentials");
	    return ResponseEntity.ok(response);
	}


}
