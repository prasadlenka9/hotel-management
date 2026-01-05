package com.hotelmanagement.service;

import com.hotelmanagement.dto.RoomImageRequest;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.entity.RoomImage;
import com.hotelmanagement.repository.RoomImageRepository;
import com.hotelmanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomImageService {

    private final RoomImageRepository imageRepository;
    private final RoomRepository roomRepository;

    public RoomImageService(
            RoomImageRepository imageRepository,
            RoomRepository roomRepository
    ) {
        this.imageRepository = imageRepository;
        this.roomRepository = roomRepository;
    }

    public RoomImage addImage(RoomImageRequest request) {

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        RoomImage image = RoomImage.builder()
                .imageUrl(request.getImageUrl())
                .isPrimary(request.isPrimary())
                .room(room)
                .build();

        return imageRepository.save(image);
    }
}
