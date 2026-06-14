package com.example.reservations_app.service;

import com.example.reservations_app.entity.Booking;
import com.example.reservations_app.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service class for managing Booking entities.
 
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository productRepository) {
        this.bookingRepository = productRepository;
    }
    //  Save a booking
    
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    //   Get all the bookings.
     
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    // Get one booking by ID.
     
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    
    //Update a booking
     
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Optional<Booking> existingProduct = bookingRepository.findById(id);
        if (existingProduct.isPresent()) {
            Booking booking = existingProduct.get();
            booking.setName(updatedBooking.getName());
            booking.setTitle(updatedBooking.getTitle());
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found");
        }
    }
    // Delete the booking by ID.
     
    public void deleteBooking(Long id) {
    	bookingRepository.deleteById(id);
    }
}