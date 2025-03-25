package com.example.photographyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.photographyProject.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	
	User findByEmail(String email);

}
