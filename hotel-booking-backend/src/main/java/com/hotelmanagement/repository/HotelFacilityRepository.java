package com.hotelmanagement.repository;

import com.hotelmanagement.entity.HotelFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelFacilityRepository extends JpaRepository<HotelFacility, Long> {

    List<HotelFacility> findByHotelId(Long hotelId);
}
