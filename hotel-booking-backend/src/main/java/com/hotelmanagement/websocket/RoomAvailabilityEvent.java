package com.hotelmanagement.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomAvailabilityEvent {

    private Long roomId;
    private boolean available;
}
