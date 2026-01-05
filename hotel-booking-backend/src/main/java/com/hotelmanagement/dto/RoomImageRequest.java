package com.hotelmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomImageRequest {

    private String imageUrl;
    private boolean isPrimary;
    private Long roomId;
}
