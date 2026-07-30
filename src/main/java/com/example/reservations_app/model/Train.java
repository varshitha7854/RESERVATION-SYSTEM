package com.example.reservations_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@JsonIgnoreProperties(value = {"availableSeats"}, allowGetters = true)
public class Train {

    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private LocalDate journeyDate;
    private List<Seat> seats = new ArrayList<>();

    public Train() {
    }

    public Train(String trainNumber, String trainName, String source,
                 String destination, LocalDate journeyDate, List<Seat> seats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.journeyDate = journeyDate;
        this.seats = new ArrayList<>(seats);
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats == null ? new ArrayList<>() : new ArrayList<>(seats);
    }

    public List<Seat> getSeatsByClass(SeatClass seatClass) {
        return filterSeats(seat -> seat.getSeatClass() == seatClass);
    }

    public List<Seat> getAvailableSeats() {
        return filterSeats(Seat::isAvailable);
    }

    public List<Seat> filterSeats(Predicate<Seat> predicate) {
        return seats.stream().filter(predicate).toList();
    }

    public long countAvailable(SeatClass seatClass) {
        return seats.stream()
                .filter(seat -> seat.getSeatClass() == seatClass && seat.isAvailable())
                .count();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Train train)) {
            return false;
        }
        return Objects.equals(trainNumber, train.trainNumber)
                && Objects.equals(journeyDate, train.journeyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainNumber, journeyDate);
    }
}
