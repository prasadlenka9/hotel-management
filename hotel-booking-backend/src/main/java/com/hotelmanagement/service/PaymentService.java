package com.hotelmanagement.service;

import com.hotelmanagement.entity.Payment;
import com.hotelmanagement.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Page<Payment> getAllPayments(int page, int size) {
        return paymentRepository.findAll(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );
    }
}
