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

    // Customer Signup
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody Customer user) {
        Map<String, String> response = new HashMap<>();

        // Check if email already exists in either Customer or Vendor
        if (userService.isEmailTaken(user.getEmail())) {
            response.put("message", "Email is already in use. Please use a different email.");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if username already exists
        if (userService.isUsernameTaken(user.getUsername())) {
            response.put("message", "Username already taken. Please choose another.");
            return ResponseEntity.badRequest().body(response);
        }

        user.setRole("customer"); // Assign role
        user.setVerified(false);
        userService.registerUser(user);

        String otp = otpService.generateOtp(user.getEmail());
        emailService.sendOtp(user.getEmail(), otp);

        response.put("message", "OTP sent to email for verification");
        return ResponseEntity.ok(response);
    }

    // Vendor (Service Provider) Signup
    @PostMapping("/service-provider-signup")
    public ResponseEntity<Map<String, String>> serviceProviderSignUp(@RequestBody ServiceProvider serviceProvider) {
        Map<String, String> response = new HashMap<>();

        // Check if email already exists in either Customer or Vendor
        if (userService.isEmailTaken(serviceProvider.getEmail())) {
            response.put("message", "Email is already in use. Please use a different email.");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if username already exists
        if (userService.isUsernameTaken(serviceProvider.getName())) {
            response.put("message", "Username already taken. Please choose another.");
            return ResponseEntity.badRequest().body(response);
        }

        serviceProvider.setRole("vendor"); // Assign role
        serviceProvider.setVerified(false);
        userService.registerVendor(serviceProvider);

        String otp = otpService.generateOtp(serviceProvider.getEmail());
        emailService.sendOtp(serviceProvider.getEmail(), otp);

        response.put("message", "OTP sent to email for verification");
        return ResponseEntity.ok(response);
    }

    // Verify OTP
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

    // Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("email") String email,
                                                     @RequestParam("password") String password,
                                                     @RequestParam("role") String role) {
        Map<String, Object> response = new HashMap<>();

        // Check if user exists
        if (!userService.isUserExists(email, role)) {
            response.put("success", false);
            response.put("message", "User not found with the given role. Please check your credentials.");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if OTP is verified
        if (!otpService.isVerified(email)) {
            response.put("success", false);
            response.put("message", "Please verify your OTP before logging in.");
            return ResponseEntity.badRequest().body(response);
        }

        // Authenticate user
        boolean authenticated = userService.authenticateUser(email, password, role);
        response.put("success", authenticated);
        response.put("message", authenticated ? "Login successful" : "Invalid credentials");

        return ResponseEntity.ok(response);
    }
}
