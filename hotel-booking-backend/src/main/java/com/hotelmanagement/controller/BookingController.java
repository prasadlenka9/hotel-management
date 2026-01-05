package com.hotelmanagement.controller;

import com.hotelmanagement.dto.BookingResponse;
import com.hotelmanagement.dto.CreateBookingRequest;
import com.hotelmanagement.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /* ===================== CREATE BOOKING ===================== */
    // USER + ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody CreateBookingRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.createBookingResponse(
                        request,
                        authentication.getName()
                )
        );
    }

    /* ===================== CANCEL BOOKING ===================== */
    // USER + ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled");
    }

    /* ===================== VIEW MY BOOKINGS (SIMPLE) ===================== */
    // USER + ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.getMyBookingResponses(
                        authentication.getName()
                )
        );
    }

    /* ===================== VIEW MY BOOKINGS (PAGINATED) ===================== */
    // USER + ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/my/paged")
    public ResponseEntity<?> getMyBookingsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.getMyBookingsPaged(
                        authentication.getName(),
                        page,
                        size
                )
        );
    }

    /* ===================== VIEW MY BOOKINGS (DATE RANGE + PAGINATED) ===================== */
    // USER + ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/my/by-date")
    public ResponseEntity<?> getMyBookingsByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.getMyBookingsByDate(
                        authentication.getName(),
                        from,
                        to,
                        page,
                        size
                )
        );
    }
}
