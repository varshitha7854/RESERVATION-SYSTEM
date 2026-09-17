package com.example.reservations_app.repository;

import com.example.reservations_app.entity.Booking;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking,Long> {

    List<Booking> findByUserId(Integer userId);
    @NullMarked
    //Optional<> findById(ID id);

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

    //CharSequence findById(Integer id);
}
