package com.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber; // e.g. 101, A-203

    @Column(nullable = false)
    private String type; // Single, Double, Deluxe, Suite

    @Column(nullable = false)
    private BigDecimal pricePerNight;

    @Column(nullable = false)
    private int maxGuests;

    @Column(length = 500)
    private String amenities; // AC, TV, Wi-Fi, Balcony

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean underMaintenance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
