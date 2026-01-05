package com.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@Entity
@Table(name = "hotel_facilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Wi-Fi, Pool, Parking, Gym

    private String icon;     // e.g. "wifi", "pool"
    private String imageUrl; // optional image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
