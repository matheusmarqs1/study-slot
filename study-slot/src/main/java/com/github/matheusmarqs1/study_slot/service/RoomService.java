package com.github.matheusmarqs1.study_slot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.matheusmarqs1.study_slot.dto.request.CreateRoomRequest;
import com.github.matheusmarqs1.study_slot.dto.request.UpdateRoomRequest;
import com.github.matheusmarqs1.study_slot.dto.response.RoomResponse;
import com.github.matheusmarqs1.study_slot.entity.Room;
import com.github.matheusmarqs1.study_slot.repository.RoomRepository;

@Service 
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> findAll(){
        return roomRepository.findAll().stream().map(RoomResponse::from).toList();
    }

    public RoomResponse findById(Long id){
        return RoomResponse.from(roomRepository.findById(id).orElseThrow());
    }

    public RoomResponse create(CreateRoomRequest request){
        Room room = Room.builder()
                .name(request.name())
                .capacity(request.capacity())
                .build();
        return RoomResponse.from(roomRepository.save(room));
    }

    public RoomResponse update(Long id, UpdateRoomRequest request){
        Room existingRoom = roomRepository.findById(id).orElseThrow();
        existingRoom.setName(request.name());
        existingRoom.setCapacity(request.capacity());
        existingRoom.setActive(request.active());
        return RoomResponse.from(roomRepository.save(existingRoom));
    }

    public void delete(Long id){
        if(!roomRepository.existsById(id)) {
            throw new RuntimeException();
        }
        roomRepository.deleteById(id);
    }
}
