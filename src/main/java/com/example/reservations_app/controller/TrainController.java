package com.example.reservations_app.controller;

import com.example.reservations_app.entity.TrainEntity;
import com.example.reservations_app.repository.TrainRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RestController
@RequestMapping("/api/v1")
public class TrainController {

    private final TrainRepository trainRepository;

    public TrainController(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @GetMapping("/trains")
    public ResponseEntity<Iterable<TrainEntity>> getAllTrains() {
        return ResponseEntity.ok(trainRepository.findAll());
    }
}
