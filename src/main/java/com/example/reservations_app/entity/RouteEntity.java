package com.example.reservations_app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "route")
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private TrainEntity train;

    @ManyToOne
    @JoinColumn(name = "source_station_id", nullable = false)
    private StationEntity sourceStation;

    @ManyToOne
    @JoinColumn(name = "destination_station_id", nullable = false)
    private StationEntity destinationStation;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TrainEntity getTrain() { return train; }
    public void setTrain(TrainEntity train) { this.train = train; }

    public StationEntity getSourceStation() { return sourceStation; }
    public void setSourceStation(StationEntity sourceStation) { this.sourceStation = sourceStation; }

    public StationEntity getDestinationStation() { return destinationStation; }
    public void setDestinationStation(StationEntity destinationStation) {
        this.destinationStation = destinationStation;
    }
}