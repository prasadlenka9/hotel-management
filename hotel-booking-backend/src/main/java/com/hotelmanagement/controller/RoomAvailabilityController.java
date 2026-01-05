package com.hotelmanagement.controller;

import com.hotelmanagement.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


// @RestController
// @RequestMapping("/api/availability")
// public class RoomAvailabilityController {

//     private final RoomService roomService;

//     public RoomAvailabilityController(RoomService roomService) {
//         this.roomService = roomService;
//     }

//     @PostMapping("/lock/{roomId}")
//     public ResponseEntity<String> lockRoom(@PathVariable Long roomId) {

//         boolean locked = roomService.lockRoom(roomId);

//         if (!locked) {
//             return ResponseEntity.badRequest()
//                     .body("Room is already locked");
//         }

//         return ResponseEntity.ok("Room locked successfully");
//     }

//     @PostMapping("/unlock/{roomId}")
//     public ResponseEntity<String> unlockRoom(@PathVariable Long roomId) {
//         roomService.unlockRoom(roomId);
//         return ResponseEntity.ok("Room unlocked");
//     }
// }





@RestController
@RequestMapping("/api/availability")
public class RoomAvailabilityController {

    private final RoomService roomService;

    public RoomAvailabilityController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/lock/{roomId}")
    public ResponseEntity<String> lockRoom(@PathVariable Long roomId) {

        boolean locked = roomService.lockRoom(roomId);

        if (!locked) {
            return ResponseEntity.badRequest()
                    .body("Room is already locked");
        }

        return ResponseEntity.ok("Room locked successfully");
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/unlock/{roomId}")
    public ResponseEntity<String> unlockRoom(@PathVariable Long roomId) {
        roomService.unlockRoom(roomId);
        return ResponseEntity.ok("Room unlocked");
    }
}
