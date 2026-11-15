package com.github.matheusmarqs1.study_slot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.matheusmarqs1.study_slot.entity.Reservation;
import com.github.matheusmarqs1.study_slot.entity.Room;
import com.github.matheusmarqs1.study_slot.entity.User;
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

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id){
        return reservationRepository.findById(id).orElseThrow();
    }

    public Reservation create(Reservation reservation){
        User user = userRepository.findById(reservation.getUser().getId()).orElseThrow();
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow();
        reservation.setUser(user);
        reservation.setRoom(room);
        return reservationRepository.save(reservation);
    }

    public Reservation update(Long id, Reservation reservation){
        Reservation existingReservation = reservationRepository.findById(id).orElseThrow();
        existingReservation.setStartsAt(reservation.getStartsAt());
        existingReservation.setEndsAt(reservation.getEndsAt());
        existingReservation.setStatus(reservation.getStatus());
        return reservationRepository.save(existingReservation);
    }

    public void delete(Long id){
        if(!reservationRepository.existsById(id)) {
            throw new RuntimeException();
        }
        reservationRepository.deleteById(id);
    }
}

