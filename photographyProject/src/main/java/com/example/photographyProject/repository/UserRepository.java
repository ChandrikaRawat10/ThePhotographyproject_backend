package com.example.photographyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.photographyProject.model.Customer;


public interface UserRepository extends JpaRepository<Customer, Long>{
	Customer findByUsername(String username);
	
	Customer findByEmail(String email);

}
