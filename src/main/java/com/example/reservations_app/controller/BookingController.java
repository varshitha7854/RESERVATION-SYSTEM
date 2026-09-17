package com.example.reservations_app.controller;

import com.example.reservations_app.entity.Booking;
import com.example.reservations_app.repository.BookingRepository;
//import com.example.reservations_app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RestController
@RequestMapping("/api/v1")

public class BookingController {
//private final BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;


//    public BookingController(BookingService bookingService) {
//        this.bookingService = bookingService;
//    }

//    @PostMapping("/bookings")
//   public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking) {
//       // return ResponseEntity.ok(bookingService.saveBooking(booking));
//    }

//    @PostMapping("/booking")
//    public ResponseEntity<Booking> saveBookingLegacy(@RequestBody Booking booking) {
//       // return saveBooking(booking);
//
    @GetMapping ("/bookings")
    public @ResponseBody Iterable<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }
    @GetMapping ("/bookings/user")
    public @ResponseBody Iterable<Booking> getAllBookingsUserid(@RequestParam Integer userId){
        return bookingRepository.findByUserId(userId);
    }
    @PostMapping("/bookings/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(savedBooking);
    }
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return ResponseEntity.ok("Booking deleted successfully");
    }
    @GetMapping("/bookings/occupied")
    public ResponseEntity<List<String>> getOccupiedSeats(
            @RequestParam String trainNumber,
            @RequestParam LocalDate journeyDate
    ) {
        List<String> occupiedSeats = bookingRepository
                .findByTrainNumberAndJourneyDateAndStatus(trainNumber, journeyDate, "CONFIRMED")
                .stream()
                .map(Booking::getSeatNumber)
                .toList();

        return ResponseEntity.ok(occupiedSeats);
    }

  //  @GetMapping("/bookings/{id}")
   // public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
    //    Booking booking = bookingRepository.findById(/id)
             //   .orElseThrow(() -> new RuntimeException("Booking not found"));
       // return ResponseEntity.ok(booking);

  //   @GetMapping("/bookings/{userId}")
   //
    //public ResponseEntity<List<Booking>> getBookingById(@PathVariable Long userId){
     //   return ResponseEntity.ok(bookingRepository.findByUserId(userId));
    //}
     



//    @GetMapping("/bookings/occupied")
//    public ResponseEntity<List<String>> getOccupiedSeats(
//            @RequestParam String trainNumber,
//            @RequestParam LocalDate journeyDate) {
//        return ResponseEntity.ok(bookingService.getOccupiedSeatNumbers(trainNumber, journeyDate));
 //   }
//
// @GetMapping("/bookings/{id}")
//   public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
//    return ResponseEntity.ok(bookingService.getBookingById(id));
//    }

//    @PutMapping("/bookings/{id}")
//    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
//        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
//    }
//
//    @DeleteMapping("/bookings/{id}")
//    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
//        bookingService.deleteBooking(id);
//        return ResponseEntity.ok("Booking deleted successfully");
//    }
}
