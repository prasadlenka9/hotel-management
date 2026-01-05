package com.hotelmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateBookingRequest {
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
