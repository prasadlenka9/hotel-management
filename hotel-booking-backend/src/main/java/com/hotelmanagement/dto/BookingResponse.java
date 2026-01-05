package com.hotelmanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class BookingResponse {

    private Long bookingId;
    private String roomNumber;
    private String roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalAmount;
    private String status;
}
