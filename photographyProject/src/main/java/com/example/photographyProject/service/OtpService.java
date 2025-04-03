package com.example.photographyProject.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private Map<String, String> otpStorage = new HashMap<>();
    private Map<String, Boolean> verifiedUsers = new HashMap<>();
    
    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(9000) + 1000);
        otpStorage.put(email, otp);
        verifiedUsers.put(email, false); // Initially, not verified
        return otp;
    }
    
    public boolean validateOtp(String email, String otp) {
        return otp.equals(otpStorage.get(email));
    }
    
    public void setVerified(String email, boolean status) {
        verifiedUsers.put(email, status);
    }
    
    public boolean isVerified(String email) {
        return verifiedUsers.getOrDefault(email, false);
    }
}
