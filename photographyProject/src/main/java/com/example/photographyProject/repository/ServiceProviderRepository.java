package com.example.photographyProject.repository;

import com.example.photographyProject.model.Customer;
import com.example.photographyProject.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    Optional<ServiceProvider> findByEmail(String email);

    Optional<ServiceProvider> findByUsername(String username);
}
