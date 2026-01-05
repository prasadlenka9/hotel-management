package com.hotelmanagement.controller;

import com.hotelmanagement.dto.AuthResponse;
import com.hotelmanagement.dto.RequestOtpRequest;
import com.hotelmanagement.dto.VerifyOtpRequest;
import com.hotelmanagement.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestOtp(@RequestBody RequestOtpRequest request) {
        otpService.requestOtp(request);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(otpService.verifyOtp(request));
    }
}
