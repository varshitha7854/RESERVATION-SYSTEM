package com.example.reservations_app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "station")
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_code", nullable = false, unique = true)
    private String stationCode;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(nullable = false)
    private String city;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStationCode() { return stationCode; }
    public void setStationCode(String stationCode) { this.stationCode = stationCode; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}

