package com.hotelmanagement.controller;

import com.hotelmanagement.entity.Hotel;
import com.hotelmanagement.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // ADMIN: add/update hotel info
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.saveHotel(hotel));
    }

    // USER + ADMIN: view hotel info
    @GetMapping
    public ResponseEntity<List<Hotel>> getHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
}
