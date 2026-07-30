package com.example.reservations_app.service;

import com.example.reservations_app.model.Seat;
import com.example.reservations_app.model.SeatClass;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SeatAllocatorService {

    public Optional<Seat> allocate(List<Seat> seats, Predicate<Seat> criteria) {
        return seats.stream()
                .filter(Seat::isAvailable)
                .filter(criteria)
                .findFirst();
    }

    public Predicate<Seat> byClass(SeatClass seatClass) {
        return seat -> seat.getSeatClass() == seatClass;
    }

    public Predicate<Seat> anyAC() {
        return seat -> seat.getSeatClass().isAC();
    }

    public Optional<Seat> allocateCheapest(List<Seat> seats, SeatClass seatClass) {
        return seats.stream()
                .filter(Seat::isAvailable)
                .filter(byClass(seatClass))
                .min(Comparator.comparingDouble(Seat::getBaseFare));
    }
}
