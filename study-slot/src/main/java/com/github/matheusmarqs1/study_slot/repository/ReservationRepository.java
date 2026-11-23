package com.github.matheusmarqs1.study_slot.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.matheusmarqs1.study_slot.entity.Reservation;
import com.github.matheusmarqs1.study_slot.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        select count(r) > 0 from Reservation r
        where r.room.id = :roomId
        and r.status = :status
        and r.startsAt < :endsAt
        and r.endsAt > :startsAt
        """)
    boolean existsOverlapping(Long roomId, ReservationStatus status, Instant startsAt, Instant endsAt);

    @Query("""
        select count(r) > 0 from Reservation r
        where r.room.id = :roomId
        and r.status = :status
        and r.id <> :excludedId
        and r.startsAt < :endsAt
        and r.endsAt > :startsAt
        """)
    boolean existsOverlappingExcluding(Long roomId, ReservationStatus status, Long excludedId, Instant startsAt, Instant endsAt);
}
