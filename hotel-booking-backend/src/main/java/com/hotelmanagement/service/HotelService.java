package com.hotelmanagement.service;

import com.hotelmanagement.entity.Hotel;
import com.hotelmanagement.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // Admin: create or update single hotel
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    // User/Admin: view hotel details
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
}
