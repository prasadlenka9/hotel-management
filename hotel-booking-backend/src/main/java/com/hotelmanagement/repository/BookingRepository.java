package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomIdAndStatusAndCheckOutDateAfterAndCheckInDateBefore(
            Long roomId,
            String status,
            LocalDate checkIn,
            LocalDate checkOut
    );

    List<Booking> findByUserId(Long userId);
    Page<Booking> findByUserId(Long userId, Pageable pageable);
    Page<Booking> findAll(Pageable pageable);
    Page<Booking> findByStatus(String status, Pageable pageable);

    Page<Booking> findByUserIdAndCheckInDateBetween(
        Long userId,
        LocalDate from,
        LocalDate to,
        Pageable pageable
    );

    Page<Booking> findByCheckInDateBetween(
        LocalDate from,
        LocalDate to,
        Pageable pageable
    );


}