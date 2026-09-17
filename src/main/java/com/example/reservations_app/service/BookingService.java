//package com.example.reservations_app.service;
//
//import com.example.reservations_app.entity.Booking;
//import com.example.reservations_app.model.Seat;
//import com.example.reservations_app.model.SeatClass;
//import com.example.reservations_app.repository.BookingRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookingService {
//
//    private static final String CONFIRMED = "CONFIRMED";
//
//    private final BookingRepository bookingRepository;
//    //private final TrainService trainService;
//
//    public BookingService(BookingRepository bookingRepository) {
//        this.bookingRepository = bookingRepository;
//        //this.trainService = trainService;
//    }
//
//    public Booking saveBooking(Booking booking) {
//
//        booking.setTitle(booking.getTrainName());
//        booking.setStatus(CONFIRMED);
//        boolean seatAllocatedByBackend = assignSeatIfNeeded(booking);
//        validateBooking(booking);
//
//        boolean seatAlreadyBooked = bookingRepository.existsByTrainNumberAndJourneyDateAndSeatNumberAndStatus(
//                booking.getTrainNumber(),
//                booking.getJourneyDate(),
//                booking.getSeatNumber(),
//                CONFIRMED
//        );
//
//        if (seatAlreadyBooked) {
//            throw new IllegalArgumentException("This seat is already booked.");
//        }
//
//        if (!seatAllocatedByBackend) {
//            //trainService.reserveSeat(booking.getTrainNumber(), booking.getJourneyDate(), booking.getSeatNumber());
//        }
//        try {
//            return bookingRepository.save(booking);
//        } catch (RuntimeException exception) {
//            //trainService.releaseSeat(booking.getTrainNumber(), booking.getJourneyDate(), booking.getSeatNumber());
//            throw exception;
//        }
//    }
//
//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }
//
//    public List<String> getOccupiedSeatNumbers(String trainNumber, java.time.LocalDate journeyDate) {
//        return bookingRepository.findByTrainNumberAndJourneyDateAndStatus(trainNumber, journeyDate, CONFIRMED)
//                .stream()
//                .map(Booking::getSeatNumber)
//                .toList();
//    }
//
//    public List<Booking> getBookingsForUser(Integer userId) {
//       // return bookingRepository.findByUserIdOrderByIdDesc(userId);
//    }
//
//    public Booking getBookingById(Long id) {
//       // return bookingRepository.findById(id)
//                //.orElseThrow(() -> new IllegalArgumentException("Booking not found."));
//    }
//
//    public Booking updateBooking(Long id, Booking updatedBooking) {
//        Booking booking = getBookingById(id);
//        String previousStatus = booking.getStatus();
//        String previousTrainNumber = booking.getTrainNumber();
//        java.time.LocalDate previousJourneyDate = booking.getJourneyDate();
//        String previousSeatNumber = booking.getSeatNumber();
//
//        booking.setTrainNumber(updatedBooking.getTrainNumber());
//        booking.setTrainName(updatedBooking.getTrainName());
//        booking.setTitle(updatedBooking.getTrainName());
//        booking.setSource(updatedBooking.getSource());
//
//        booking.setJourneyDate(updatedBooking.getJourneyDate());
//        booking.setSeatClass(updatedBooking.getSeatClass());
//        booking.setSeatNumber(updatedBooking.getSeatNumber());
//        booking.setFare(updatedBooking.getFare());
//        booking.setStatus(updatedBooking.getStatus());
//        validateBooking(booking);
//        Booking savedBooking = bookingRepository.save(booking);
//
//        if (CONFIRMED.equals(previousStatus) && "CANCELLED".equals(savedBooking.getStatus())) {
//           // trainService.releaseSeat(previousTrainNumber, previousJourneyDate, previousSeatNumber);
//        }
//
//        return savedBooking;
//    }
//
//    public void deleteBooking(Long id) {
//        Booking booking = getBookingById(id);
//        bookingRepository.deleteById(id);
//        //trainService.releaseSeat(booking.getTrainNumber(), booking.getJourneyDate(), booking.getSeatNumber());
//    }
//
//    private boolean assignSeatIfNeeded(Booking booking) {
//        if (!isBlank(booking.getSeatNumber())) {
//            return false;
//        }
//
//        if (isBlank(booking.getSeatClass())
//                || isBlank(booking.getTrainNumber())
//                || booking.getJourneyDate() == null) {
//            return false;
//        }
//
//       // SeatClass seatClass = SeatClass.fromValue(booking.getSeatClass());
//        //Seat seat = trainService.allocateSeat(booking.getTrainNumber(), booking.getJourneyDate(), seatClass);
//       // booking.setSeatNumber(seat.getSeatNumber());
//       // if (booking.getFare() == null) {
//        //    booking.setFare(seat.getBaseFare());
//        //  }
//        return true;
//    }
//
//    private void validateBooking(Booking booking) {
//        if (isBlank(booking.getTrainNumber())
//                || isBlank(booking.getTrainName())
//                || isBlank(booking.getSource())
//
//                || booking.getJourneyDate() == null
//                || isBlank(booking.getSeatClass())
//                || isBlank(booking.getSeatNumber())
//                || booking.getFare() == null
//                || booking.getFare() < 0
//                ) {
//            throw new IllegalArgumentException("All booking fields are required.");
//        }
//    }
//
//    private String normalizeEmail(String email) {
//        return email == null ? null : email.trim().toLowerCase();
//    }
//
//    private boolean isBlank(String value) {
//        return value == null || value.trim().isEmpty();
//    }
//}
