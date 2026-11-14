package com.github.matheusmarqs1.study_slot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.matheusmarqs1.study_slot.entity.Reservation;
import com.github.matheusmarqs1.study_slot.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }
}
