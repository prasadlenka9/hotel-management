package com.hotelmanagement.service;

import com.hotelmanagement.dto.AuthResponse;
import com.hotelmanagement.dto.RequestOtpRequest;
import com.hotelmanagement.dto.VerifyOtpRequest;
import com.hotelmanagement.entity.OtpRequest;
import com.hotelmanagement.entity.Role;
import com.hotelmanagement.entity.User;
import com.hotelmanagement.repository.OtpRequestRepository;
import com.hotelmanagement.repository.RoleRepository;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRequestRepository otpRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    public OtpService(
            OtpRequestRepository otpRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            JwtUtil jwtUtil
    ) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    public void requestOtp(RequestOtpRequest request) {

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        OtpRequest otpRequest = OtpRequest.builder()
                .phoneNumber(request.getPhoneNumber())
                .otp(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(otpRequest);

        // Simulated SMS
        System.out.println("OTP for " + request.getPhoneNumber() + " is: " + otp);
    }

    public AuthResponse verifyOtp(VerifyOtpRequest request) {

        OtpRequest otpRequest = otpRepository
                .findTopByPhoneNumberOrderByExpiresAtDesc(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otpRequest.isVerified()) {
            throw new RuntimeException("OTP already used");
        }

        if (otpRequest.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otpRequest.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        otpRequest.setVerified(true);
        otpRepository.save(otpRequest);

        User user = userRepository.findByEmail(request.getPhoneNumber())
                .orElseGet(() -> {

                    Role role = roleRepository.findByName("USER")
                            .orElseThrow(() -> new RuntimeException("ROLE USER not found"));

                    User newUser = User.builder()
                            .phoneNumber(request.getPhoneNumber())
                            .provider("OTP")
                            .isVerified(true)
                            .role(role)
                            .build();

                    return userRepository.save(newUser);
                });

        String token = jwtUtil.generateToken(
                user.getPhoneNumber(),
                user.getRole().getName()
        );

        return new AuthResponse(token);
    }
}
