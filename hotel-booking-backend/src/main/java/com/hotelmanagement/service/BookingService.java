package com.hotelmanagement.service;

import com.hotelmanagement.dto.BookingResponse;
import com.hotelmanagement.dto.CreateBookingRequest;
import com.hotelmanagement.entity.Booking;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.entity.User;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.RoomRepository;
import com.hotelmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomService roomService;
    private final DummyPaymentService paymentService;
    private final NotificationService notificationService;

    public BookingService(
            BookingRepository bookingRepository,
            RoomRepository roomRepository,
            UserRepository userRepository,
            RoomService roomService,
            DummyPaymentService paymentService,
            NotificationService notificationService
    ) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.roomService = roomService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    /* ===================== CREATE BOOKING ===================== */

    @Transactional
    public BookingResponse createBookingResponse(
            CreateBookingRequest request,
            String username
    ) {

        // Validate date range
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new RuntimeException("Invalid date range");
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check overlapping confirmed bookings
        List<Booking> overlaps =
                bookingRepository.findByRoomIdAndStatusAndCheckOutDateAfterAndCheckInDateBefore(
                        room.getId(),
                        "CONFIRMED",
                        request.getCheckInDate(),
                        request.getCheckOutDate()
                );

        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Room already booked for selected dates");
        }

        // Lock room (real-time)
        boolean locked = roomService.lockRoom(room.getId());
        if (!locked) {
            throw new RuntimeException("Room is temporarily unavailable");
        }

        try {
            long nights = ChronoUnit.DAYS.between(
                    request.getCheckInDate(),
                    request.getCheckOutDate()
            );

            BigDecimal totalAmount =
                    room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

            // Dummy payment
            paymentService.processPayment();

            Booking booking = Booking.builder()
                    .user(user)
                    .room(room)
                    .checkInDate(request.getCheckInDate())
                    .checkOutDate(request.getCheckOutDate())
                    .totalAmount(totalAmount)
                    .status("CONFIRMED")
                    .build();

            Booking savedBooking = bookingRepository.save(booking);

            // 🔔 Notification
            notificationService.sendBookingConfirmationEmail(savedBooking);

            return mapToResponse(savedBooking);

        } catch (Exception ex) {
            roomService.unlockRoom(room.getId());
            throw ex;
        }
    }

    /* ===================== USER BOOKINGS BY DATE ===================== */

public Page<BookingResponse> getMyBookingsByDate(
        String username,
        LocalDate from,
        LocalDate to,
        int page,
        int size
) {

    User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    PageRequest pageable =
            PageRequest.of(page, size, Sort.by("createdAt").descending());

    Page<Booking> bookingPage =
            bookingRepository.findByUserIdAndCheckInDateBetween(
                    user.getId(),
                    from,
                    to,
                    pageable
            );

    return bookingPage.map(this::mapToResponse);
}

/* ===================== ADMIN BOOKINGS BY DATE ===================== */

public Page<BookingResponse> getAllBookingsByDate(
        LocalDate from,
        LocalDate to,
        int page,
        int size
) {

    PageRequest pageable =
            PageRequest.of(page, size, Sort.by("createdAt").descending());

    Page<Booking> bookingPage =
            bookingRepository.findByCheckInDateBetween(
                    from,
                    to,
                    pageable
            );

    return bookingPage.map(this::mapToResponse);
}


    /* ===================== CANCEL BOOKING ===================== */

    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        // 🔔 Notification
        notificationService.sendBookingCancellationEmail(booking);

        // Release room
        roomService.unlockRoom(booking.getRoom().getId());
    }

    /* ===================== ADMIN BOOKINGS ===================== */

public Page<BookingResponse> getAllBookingsPaged(
        String status,
        int page,
        int size
) {

    PageRequest pageable =
            PageRequest.of(page, size, Sort.by("createdAt").descending());

    Page<Booking> bookingPage;

    if (status != null && !status.isBlank()) {
        bookingPage = bookingRepository.findByStatus(status, pageable);
    } else {
        bookingPage = bookingRepository.findAll(pageable);
    }

    return bookingPage.map(this::mapToResponse);
}



    /* ===================== VIEW MY BOOKINGS ===================== */

    public List<BookingResponse> getMyBookingResponses(String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<BookingResponse> getMyBookingsPaged(
        String username,
        int page,
        int size
) {

    User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Page<Booking> bookingPage =
            bookingRepository.findByUserId(
                    user.getId(),
                    PageRequest.of(page, size, Sort.by("createdAt").descending())
            );

    return bookingPage.map(this::mapToResponse);
}

    /* ===================== MAPPER ===================== */

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .roomNumber(booking.getRoom().getRoomNumber())
                .roomType(booking.getRoom().getType())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .build();
    }
}
