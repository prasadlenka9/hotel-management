package com.hotelmanagement.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DummyPaymentService {

    public String processPayment() {
        // Simulate 90% success
        boolean success = Math.random() < 0.9;
        if (!success) {
            throw new RuntimeException("Payment failed");
        }
        return UUID.randomUUID().toString();
    }
}
