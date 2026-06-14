package com.example.reservations_app.controller;

import com.example.reservations_app.entity.Booking;
import com.example.reservations_app.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create a new booking.

    @PostMapping("/booking")
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking) {
        Booking newBooking = bookingService.saveBooking(booking);
        return ResponseEntity.ok(newBooking);
    }

    // Get all bookings.
     
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Get a booking by ID.
    
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a booking by ID.
     
    @PutMapping("/bookings/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking product) {
        Booking updatedBooking = bookingService.updateBooking(id, product);
        return ResponseEntity.ok(updatedBooking);
    }

    // Delete a booking by ID.
     
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
    	bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully");
    }
}