package com.github.matheusmarqs1.study_slot.dto.request;

import java.time.Instant;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record UpdateReservationRequest(
    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    Instant startsAt,

    @NotNull(message = "End time is required")
    Instant endsAt
) {
    @AssertTrue(message = "End time must be after start time")
    public boolean isEndAfterStart(){
        return startsAt == null || endsAt == null || endsAt.isAfter(startsAt);
    }
}
