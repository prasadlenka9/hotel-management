package com.hotelmanagement.service;

import com.hotelmanagement.entity.Room;
import com.hotelmanagement.repository.RoomRepository;
import com.hotelmanagement.websocket.AvailabilityPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomLockService roomLockService;
    private final AvailabilityPublisher availabilityPublisher;

    public RoomService(
            RoomRepository roomRepository,
            RoomLockService roomLockService,
            AvailabilityPublisher availabilityPublisher
    ) {
        this.roomRepository = roomRepository;
        this.roomLockService = roomLockService;
        this.availabilityPublisher = availabilityPublisher;
    }

    /* ===================== ADMIN METHODS ===================== */

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void toggleRoomActive(Long roomId, boolean active) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setActive(active);
        roomRepository.save(room);

        // Broadcast availability change
        availabilityPublisher.publish(roomId, active && !room.isUnderMaintenance());
    }

    public void setMaintenance(Long roomId, boolean maintenance) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setUnderMaintenance(maintenance);
        roomRepository.save(room);

        // Broadcast availability change
        availabilityPublisher.publish(roomId, room.isActive() && !maintenance);
    }

    /* ===================== USER METHODS ===================== */

    public List<Room> getAvailableRooms(Long hotelId) {
        return roomRepository
                .findByHotelIdAndActiveTrueAndUnderMaintenanceFalse(hotelId);
    }

    /* ===================== REAL-TIME AVAILABILITY ===================== */

    /**
     * Temporarily lock a room during booking
     */
    public boolean lockRoom(Long roomId) {

        boolean locked = roomLockService.lockRoom(roomId);

        if (locked) {
            // Notify all clients that room is unavailable
            availabilityPublisher.publish(roomId, false);
        }

        return locked;
    }

    /**
     * Unlock room after booking success/failure
     */
    public void unlockRoom(Long roomId) {

        roomLockService.unlockRoom(roomId);

        // Notify all clients that room is available again
        availabilityPublisher.publish(roomId, true);
    }

    /**
     * Check if room is currently locked
     */
    public boolean isRoomLocked(Long roomId) {
        return roomLockService.isRoomLocked(roomId);
    }
}
