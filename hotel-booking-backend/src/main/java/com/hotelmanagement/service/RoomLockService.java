package com.hotelmanagement.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomLockService {

    private static final long LOCK_DURATION_MINUTES = 5;

    private final Map<Long, LocalDateTime> lockedRooms = new ConcurrentHashMap<>();

    public boolean lockRoom(Long roomId) {

        LocalDateTime now = LocalDateTime.now();

        // If room already locked and not expired
        if (lockedRooms.containsKey(roomId)) {
            LocalDateTime expiry = lockedRooms.get(roomId);
            if (expiry.isAfter(now)) {
                return false;
            }
        }

        lockedRooms.put(roomId, now.plusMinutes(LOCK_DURATION_MINUTES));
        return true;
    }

    public void unlockRoom(Long roomId) {
        lockedRooms.remove(roomId);
    }

    public boolean isRoomLocked(Long roomId) {
        return lockedRooms.containsKey(roomId)
                && lockedRooms.get(roomId).isAfter(LocalDateTime.now());
    }
}
