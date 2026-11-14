package com.github.matheusmarqs1.study_slot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.matheusmarqs1.study_slot.entity.Reservation;
import com.github.matheusmarqs1.study_slot.repository.ReservationRepository;

@Service 
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }
}
