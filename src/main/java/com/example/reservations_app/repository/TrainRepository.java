package com.example.reservations_app.repository;

import com.example.reservations_app.entity.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {
}