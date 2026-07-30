package com.example.reservations_app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "train_schedule")
public class TrainScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;

    @Column(name = "journey_date", nullable = false)
    private LocalDate journeyDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RouteEntity getRoute() { return route; }
    public void setRoute(RouteEntity route) { this.route = route; }

    public LocalDate getJourneyDate() { return journeyDate; }
    public void setJourneyDate(LocalDate journeyDate) { this.journeyDate = journeyDate; }
}