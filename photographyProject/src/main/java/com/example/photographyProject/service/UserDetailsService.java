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

    // ✅ Implement authenticateUser method
    public boolean authenticateUser(String email, String password) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return customer.get().getPassword().equals(password);
        }

        Optional<ServiceProvider> vendor = vendorRepository.findByEmail(email);
        return vendor.isPresent() && vendor.get().getPassword().equals(password);
    }
}
