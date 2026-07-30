package com.example.reservations_app.controller;

import com.example.reservations_app.entity.Booking;
import com.example.reservations_app.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RestController
@RequestMapping("/api/v1")

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.saveBooking(booking));
    }

    @PostMapping("/booking")
    public ResponseEntity<Booking> saveBookingLegacy(@RequestBody Booking booking) {
        return saveBooking(booking);
    }

    @GetMapping("/bookings/my")
    public ResponseEntity<List<Booking>> getMyBookings(@RequestParam Integer userId) {
        // return ResponseEntity.ok(bookingService.getBookingsForUser(email));
          return ResponseEntity.ok(bookingService.getBookingsForUser(userId));

    }


    @GetMapping("/bookings/occupied")
    public ResponseEntity<List<String>> getOccupiedSeats(
            @RequestParam String trainNumber,
            @RequestParam LocalDate journeyDate) {
        return ResponseEntity.ok(bookingService.getOccupiedSeatNumbers(trainNumber, journeyDate));
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully");
    }
}
