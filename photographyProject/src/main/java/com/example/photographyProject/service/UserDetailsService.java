package com.example.photographyProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.photographyProject.model.Customer;

import com.example.photographyProject.repository.UserRepository;

@Service
public class UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	public String registerUser(Customer user) {
		if(userRepo.findByUsername(user.getUsername()) != null) {
			return "Username already taken";
		}
		
		if(userRepo.findByEmail(user.getEmail()) != null) {
			return "Email already in use";
		}
		
		userRepo.save(user);
		return "User registered successfully";
	}
	
	
	public boolean authenticateUser(String username, String password) {
		Customer user = userRepo.findByUsername(username);
	    return user != null && user.getPassword().equals(password);
	}
}
