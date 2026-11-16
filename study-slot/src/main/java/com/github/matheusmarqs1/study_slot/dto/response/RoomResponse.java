package com.github.matheusmarqs1.study_slot.dto.response;

import com.github.matheusmarqs1.study_slot.entity.Room;

public record RoomResponse(
    Long id,
    String name,
    Integer capacity,
    Boolean active
) {
    public static RoomResponse from(Room room){
        return new RoomResponse(
            room.getId(),
            room.getName(),
            room.getCapacity(),
            room.getActive()
        );
    }
}
