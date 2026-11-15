package com.github.matheusmarqs1.study_slot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.matheusmarqs1.study_slot.entity.Room;
import com.github.matheusmarqs1.study_slot.repository.RoomRepository;

@Service 
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room findById(Long id){
        return roomRepository.findById(id).orElseThrow();
    }

    public Room create(Room room){
        return roomRepository.save(room);
    }

    public Room update(Long id, Room room){
        Room existingRoom = roomRepository.findById(id).orElseThrow();
        existingRoom.setName(room.getName());
        existingRoom.setCapacity(room.getCapacity());
        existingRoom.setActive(room.getActive());
        return roomRepository.save(existingRoom);
    }

    public void delete(Long id){
        if(!roomRepository.existsById(id)) {
            throw new RuntimeException();
        }
        roomRepository.deleteById(id);
    }
}
