package com.github.matheusmarqs1.study_slot.dto.response;

import java.time.Instant;

import com.github.matheusmarqs1.study_slot.entity.User;

public record UserResponse(
    Long id,
    String name,
    String email,
    Instant createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }
}
