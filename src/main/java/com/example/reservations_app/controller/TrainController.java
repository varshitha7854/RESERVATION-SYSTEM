//package com.example.reservations_app.controller;
//
//import com.example.reservations_app.model.Seat;
//import com.example.reservations_app.model.Train;
////import com.example.reservations_app.service.TrainService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
//@RestController
//@RequestMapping("/api/v1/trains")
//public class TrainController {
//
//    private final TrainService trainService;
//
//    public TrainController(TrainService trainService) {
//        this.trainService = trainService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Train>> getAllTrains() {
//        return ResponseEntity.ok(trainService.findAll());
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<Train>> searchTrains(
//            @RequestParam String source,
//            @RequestParam String destination,
//            @RequestParam LocalDate journeyDate) {
//        return ResponseEntity.ok(trainService.searchTrains(source, destination, journeyDate));
//    }
//
//    @GetMapping("/{trainNumber}/seats")
//    public ResponseEntity<List<Seat>> getSeats(
//            @PathVariable String trainNumber,
//            @RequestParam LocalDate journeyDate) {
//        Train train = trainService.findByNumberAndDate(trainNumber, journeyDate)
//                .orElseThrow(() -> new IllegalArgumentException("Train not found: " + trainNumber));
//        return ResponseEntity.ok(train.getSeats());
//    }
//
//    @PostMapping
//    public ResponseEntity<Train> saveTrain(@RequestBody Train train) {
//        return ResponseEntity.ok(trainService.saveTrain(train));
//    }
//}
