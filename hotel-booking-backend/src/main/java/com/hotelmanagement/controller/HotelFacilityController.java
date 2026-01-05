package com.hotelmanagement.controller;

import com.hotelmanagement.entity.HotelFacility;
import com.hotelmanagement.service.HotelFacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel/facilities")
public class HotelFacilityController {

    private final HotelFacilityService facilityService;

    public HotelFacilityController(HotelFacilityService facilityService) {
        this.facilityService = facilityService;
    }

    // ADMIN: add facility (icon + image URL)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HotelFacility> addFacility(@RequestBody HotelFacility facility) {
        return ResponseEntity.ok(facilityService.saveFacility(facility));
    }

    // USER + ADMIN: view facilities
    @GetMapping("/{hotelId}")
    public ResponseEntity<List<HotelFacility>> getFacilities(@PathVariable Long hotelId) {
        return ResponseEntity.ok(facilityService.getFacilitiesByHotel(hotelId));
    }
}
