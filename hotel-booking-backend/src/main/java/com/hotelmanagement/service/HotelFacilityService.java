package com.hotelmanagement.service;

import com.hotelmanagement.entity.HotelFacility;
import com.hotelmanagement.repository.HotelFacilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelFacilityService {

    private final HotelFacilityRepository facilityRepository;

    public HotelFacilityService(HotelFacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public HotelFacility saveFacility(HotelFacility facility) {
        return facilityRepository.save(facility);
    }

    public List<HotelFacility> getFacilitiesByHotel(Long hotelId) {
        return facilityRepository.findByHotelId(hotelId);
    }
}
