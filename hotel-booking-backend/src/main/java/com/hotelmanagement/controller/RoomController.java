package com.hotelmanagement.controller;

import com.hotelmanagement.entity.Room;
import com.hotelmanagement.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // ADMIN: add or update room
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
        return ResponseEntity.ok(roomService.saveRoom(room));
    }

    // ADMIN: enable / disable room
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{roomId}/active")
    public ResponseEntity<Void> toggleRoom(
            @PathVariable Long roomId,
            @RequestParam boolean active
    ) {
        roomService.toggleRoomActive(roomId, active);
        return ResponseEntity.ok().build();
    }

    // ADMIN: maintenance mode
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{roomId}/maintenance")
    public ResponseEntity<Void> setMaintenance(
            @PathVariable Long roomId,
            @RequestParam boolean maintenance
    ) {
        roomService.setMaintenance(roomId, maintenance);
        return ResponseEntity.ok().build();
    }

    // USER: view available rooms
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Room>> getAvailableRooms(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getAvailableRooms(hotelId));
    }
}
