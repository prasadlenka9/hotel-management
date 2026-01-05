package com.hotelmanagement.controller;

import com.hotelmanagement.dto.RoomImageRequest;
import com.hotelmanagement.entity.RoomImage;
import com.hotelmanagement.service.RoomImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms/images")
public class RoomImageController {

    private final RoomImageService imageService;

    public RoomImageController(RoomImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoomImage> addImage(
            @RequestBody RoomImageRequest request
    ) {
        return ResponseEntity.ok(imageService.addImage(request));
    }
}
