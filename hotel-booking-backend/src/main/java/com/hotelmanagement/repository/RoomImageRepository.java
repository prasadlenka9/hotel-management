package com.hotelmanagement.repository;

import com.hotelmanagement.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

    List<RoomImage> findByRoomId(Long roomId);
}
