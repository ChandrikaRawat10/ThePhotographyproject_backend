package com.example.photographyProject.service;

import com.example.photographyProject.model.Customer;
import com.example.photographyProject.model.ServiceProvider;
import com.example.photographyProject.repository.CustomerRepository;
import com.example.photographyProject.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceProviderRepository vendorRepository;

    public boolean isCustomerExists(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

    public boolean isVendorExists(String email) {
        return vendorRepository.findByEmail(email).isPresent();
    }

    public boolean isEmailTaken(String email) {
        return isCustomerExists(email) || isVendorExists(email);
    }

    public boolean isUsernameTaken(String username) {
        return customerRepository.findByUsername(username).isPresent() || 
               vendorRepository.findByUsername(username).isPresent();
    }

    public void setCustomerVerified(String email) {
        Customer user = customerRepository.findByEmail(email).orElseThrow();
        user.setVerified(true);
        customerRepository.save(user);
    }

    public void setVendorVerified(String email) {
        ServiceProvider vendor = vendorRepository.findByEmail(email).orElseThrow();
        vendor.setVerified(true);
        vendorRepository.save(vendor);
    }

    public void registerUser(Customer user) {
        customerRepository.save(user);
    }

    public void registerVendor(ServiceProvider vendor) {
        vendorRepository.save(vendor);
    }

    public boolean isUserExists(String email, String role) {
        if (role.equalsIgnoreCase("customer")) {
            return isCustomerExists(email);
        } else if (role.equalsIgnoreCase("vendor")) {
            return isVendorExists(email);
        }
        return false;
    }

    public boolean authenticateUser(String email, String password, String role) {
        if (role.equalsIgnoreCase("customer")) {
            return customerRepository.findByEmail(email)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
        } else if (role.equalsIgnoreCase("vendor")) {
            return vendorRepository.findByEmail(email)
                .map(vendor -> vendor.getPassword().equals(password))
                .orElse(false);
        }
        return false;
    }
}
