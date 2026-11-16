package com.github.matheusmarqs1.study_slot.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateRoomRequest(
    @NotBlank(message = "Room name cannot be blank")
    @Size(max = 120, message = "Room name cannot exceed 120 characters")
    String name,

    @NotNull(message = "Room capacity cannot be null")
    @Min(value = 1, message = "Room capacity must be at least 1")
    Integer capacity,

    @NotNull(message = "Room active status cannot be null")
    Boolean active
) {
}
