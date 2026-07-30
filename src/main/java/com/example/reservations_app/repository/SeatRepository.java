package com.example.reservations_app.repository;

import com.example.reservations_app.entity.SeatEntity;
import com.example.reservations_app.entity.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    Optional<SeatEntity> findByTrainAndSeatNumberIgnoreCase(TrainEntity train, String seatNumber);
}
