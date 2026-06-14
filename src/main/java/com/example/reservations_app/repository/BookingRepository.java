package com.example.reservations_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reservations_app.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
