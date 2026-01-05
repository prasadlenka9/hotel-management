package com.hotelmanagement.service;

import com.hotelmanagement.entity.Booking;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    /* ================= EMAIL (SIMULATED) ================= */

    public void sendBookingConfirmationEmail(Booking booking) {
        System.out.println("EMAIL SENT");
        System.out.println("To: " + booking.getUser().getEmail());
        System.out.println("Subject: Booking Confirmed");
        System.out.println(
                "Your booking for room " +
                booking.getRoom().getRoomNumber() +
                " is confirmed from " +
                booking.getCheckInDate() +
                " to " +
                booking.getCheckOutDate()
        );
    }

    public void sendBookingCancellationEmail(Booking booking) {
        System.out.println("EMAIL SENT");
        System.out.println("To: " + booking.getUser().getEmail());
        System.out.println("Subject: Booking Cancelled");
        System.out.println(
                "Your booking for room " +
                booking.getRoom().getRoomNumber() +
                " has been cancelled."
        );
    }

    /* ================= SMS (SIMULATED) ================= */

    public void sendSms(String phoneNumber, String message) {
        System.out.println("SMS SENT");
        System.out.println("To: " + phoneNumber);
        System.out.println("Message: " + message);
    }
}
