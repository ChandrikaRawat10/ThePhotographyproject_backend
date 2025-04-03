package com.example.photographyProject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.photographyProject.model.Customer;
import com.example.photographyProject.model.ServiceProvider;
import com.example.photographyProject.service.EmailService;
import com.example.photographyProject.service.OtpService;
import com.example.photographyProject.service.UserDetailsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody Customer user) {
        Map<String, String> response = new HashMap<>();

        if (userService.isCustomerExists(user.getEmail())) {
            response.put("message", "User already exists. Please log in.");
            return ResponseEntity.badRequest().body(response);
        }
        
        user.setVerified(false);
        userService.registerUser(user);
        String otp = otpService.generateOtp(user.getEmail());
        emailService.sendOtp(user.getEmail(), otp);

        response.put("message", "OTP sent to email for verification");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/service-provider-signup")
    public ResponseEntity<Map<String, String>> serviceProviderSignUp(@RequestBody ServiceProvider serviceProvider) {
        Map<String, String> response = new HashMap<>();

        if (userService.isVendorExists(serviceProvider.getEmail())) {
            response.put("message", "Service provider already exists. Please log in.");
            return ResponseEntity.badRequest().body(response);
        }
        
        serviceProvider.setVerified(false);
        userService.registerVendor(serviceProvider);
        String otp = otpService.generateOtp(serviceProvider.getEmail());
        emailService.sendOtp(serviceProvider.getEmail(), otp);

        response.put("message", "OTP sent to email for verification");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestParam("email") String email, 
                                                          @RequestParam("otp") String otp) {
        boolean isValid = otpService.validateOtp(email, otp);
        Map<String, String> response = new HashMap<>();

        if (isValid) {
            otpService.setVerified(email, true);
            
            if (userService.isCustomerExists(email)) {
                userService.setCustomerVerified(email);
            } else if (userService.isVendorExists(email)) {
                userService.setVendorVerified(email);
            } else {
                response.put("message", "User not found. Please sign up first.");
                response.put("isVerified", "false");
                return ResponseEntity.badRequest().body(response);
            }

            response.put("message", "OTP verified. Signup successful, now you can log in.");
            response.put("isVerified", "true");
        } else {
            response.put("message", "Invalid OTP");
            response.put("isVerified", "false");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("email") String email,
                                                     @RequestParam("password") String password) {
        Map<String, Object> response = new HashMap<>();

        if (!userService.isCustomerExists(email) && !userService.isVendorExists(email)) {
            response.put("success", false);
            response.put("message", "User not found. Please sign up first.");
            return ResponseEntity.badRequest().body(response);
        }

        boolean isVerified = otpService.isVerified(email);
        if (!isVerified) {
            response.put("success", false);
            response.put("message", "Please verify your OTP before logging in.");
            return ResponseEntity.badRequest().body(response);
        }

        // Authenticate user
        boolean authenticated = userService.authenticateUser(email, password);
        response.put("success", authenticated);
        response.put("message", authenticated ? "Login successful" : "Invalid credentials");

        return ResponseEntity.ok(response);
    }
}
