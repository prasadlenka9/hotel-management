package com.hotelmanagement.repository;

import com.hotelmanagement.entity.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRequestRepository extends JpaRepository<OtpRequest, Long> {

    Optional<OtpRequest> findTopByPhoneNumberOrderByExpiresAtDesc(String phoneNumber);
}
