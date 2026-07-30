package com.example.reservations_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reservations_app.entity.Booking;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByIdDesc(Integer userId);

    List<Booking> findByTrainNumberAndJourneyDateAndStatus(
            String trainNumber,
            LocalDate journeyDate,
            String status
    );

    boolean existsByTrainNumberAndJourneyDateAndSeatNumberAndStatus(
            String trainNumber,
            LocalDate journeyDate,
            String seatNumber,
            String status
    );
}
