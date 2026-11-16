package com.github.matheusmarqs1.study_slot.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 120, message = "Name cannot exceed 120 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Size(max = 160, message = "Email cannot exceed 160 characters")
    @Email(message = "Email is invalid")
    String email
) {

}
