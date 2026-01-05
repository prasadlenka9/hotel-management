package com.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String address;

    private String contactEmail;

    private String contactPhone;

    private String checkInTime;   // e.g. 12:00 PM
    private String checkOutTime;  // e.g. 11:00 AM

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
