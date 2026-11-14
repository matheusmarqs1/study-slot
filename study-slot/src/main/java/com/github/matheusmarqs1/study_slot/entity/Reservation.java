package com.github.matheusmarqs1.study_slot.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant startsAt;
    
    private Instant endsAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.ACTIVE;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id")
    private Room room;
}
