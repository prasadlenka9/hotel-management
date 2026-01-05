package com.hotelmanagement.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public AvailabilityPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(Long roomId, boolean available) {
        RoomAvailabilityEvent event =
                new RoomAvailabilityEvent(roomId, available);

        messagingTemplate.convertAndSend("/topic/availability", event);
    }
}
