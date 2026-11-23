package com.github.matheusmarqs1.study_slot.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;


import com.github.matheusmarqs1.study_slot.dto.request.CreateReservationRequest;
import com.github.matheusmarqs1.study_slot.dto.request.UpdateReservationRequest;
import com.github.matheusmarqs1.study_slot.dto.response.ReservationResponse;
import com.github.matheusmarqs1.study_slot.entity.Reservation;
import com.github.matheusmarqs1.study_slot.entity.ReservationStatus;
import com.github.matheusmarqs1.study_slot.entity.Room;
import com.github.matheusmarqs1.study_slot.entity.User;
import com.github.matheusmarqs1.study_slot.exception.ReservationConflictException;
import com.github.matheusmarqs1.study_slot.exception.ResourceNotFoundException;
import com.github.matheusmarqs1.study_slot.repository.ReservationRepository;
import com.github.matheusmarqs1.study_slot.repository.RoomRepository;
import com.github.matheusmarqs1.study_slot.repository.UserRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public List<ReservationResponse> findAll(){
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse findById(Long id){
        return ReservationResponse.from(reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id)));
    }

    public ReservationResponse create(CreateReservationRequest request){
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new ResourceNotFoundException("User", request.userId()));
        Room room = roomRepository.findById(request.roomId()).orElseThrow(() -> new ResourceNotFoundException("Room", request.roomId()));

        checkNoOverlap(null, room.getId(), request.startsAt(), request.endsAt());

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .startsAt(request.startsAt())
                .endsAt(request.endsAt())
                .build();
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    public ReservationResponse update(Long id, UpdateReservationRequest request){
        Reservation existingReservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));

        checkNoOverlap(id, existingReservation.getRoom().getId(), request.startsAt(), request.endsAt());

        existingReservation.setStartsAt(request.startsAt());
        existingReservation.setEndsAt(request.endsAt());
        return ReservationResponse.from(reservationRepository.save(existingReservation));
    }

    public void delete(Long id){
        if(!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private void checkNoOverlap(Long excludedId, Long roomId, Instant startsAt, Instant endsAt) {
        boolean overlaps = (excludedId == null)
                ? reservationRepository.existsOverlapping(roomId, ReservationStatus.ACTIVE, startsAt, endsAt)
                : reservationRepository.existsOverlappingExcluding(roomId, ReservationStatus.ACTIVE, excludedId, startsAt, endsAt);
        
        if(overlaps) {
            throw new ReservationConflictException("The room is already reserved for the given time range");
        }
    }
}

