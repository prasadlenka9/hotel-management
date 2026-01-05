package com.hotelmanagement.repository;

import com.hotelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelIdAndActiveTrueAndUnderMaintenanceFalse(Long hotelId);
}
