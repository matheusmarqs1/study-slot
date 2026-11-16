package com.github.matheusmarqs1.study_slot.dto.response;

import java.time.Instant;

import com.github.matheusmarqs1.study_slot.entity.Reservation;
import com.github.matheusmarqs1.study_slot.entity.ReservationStatus;

public record ReservationResponse(
    Long id,
    UserSummary user,
    RoomSummary room,
    Instant startsAt,
    Instant endsAt,
    ReservationStatus status,
    Instant createdAt

) {
    public record UserSummary(Long id, String name) {}
    public record RoomSummary(Long id, String name) {}

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            new UserSummary(reservation.getUser().getId(), reservation.getUser().getName()),
            new RoomSummary(reservation.getRoom().getId(), reservation.getRoom().getName()),
            reservation.getStartsAt(),
            reservation.getEndsAt(),
            reservation.getStatus(),
            reservation.getCreatedAt()
        );
    }
}
