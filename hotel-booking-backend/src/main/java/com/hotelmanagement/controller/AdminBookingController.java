package com.hotelmanagement.controller;

import com.hotelmanagement.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /* ===================== ADMIN: ALL BOOKINGS (PAGED + STATUS) ===================== */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllBookings(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(
                bookingService.getAllBookingsPaged(status, page, size)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
@PostMapping("/{bookingId}/cancel")
public ResponseEntity<String> adminCancelBooking(
        @PathVariable Long bookingId
) {
    bookingService.cancelBooking(bookingId);
    return ResponseEntity.ok("Booking cancelled by admin");
}


    /* ===================== ADMIN: BOOKINGS BY DATE (PAGED) ===================== */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-date")
    public ResponseEntity<?> getAllBookingsByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(
                bookingService.getAllBookingsByDate(
                        from,
                        to,
                        page,
                        size
                )
        );
    }
}
