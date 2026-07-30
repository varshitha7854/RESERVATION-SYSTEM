package com.example.reservations_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonIgnoreProperties(value = {"seatClass", "baseFare", "available"}, allowGetters = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "seatType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SleeperSeat.class, name = "SLEEPER"),
        @JsonSubTypes.Type(value = ACSeat.class, name = "AC")
})
public abstract class Seat {

    private String seatNumber;
    private boolean booked;

    protected Seat() {
    }

    protected Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.booked = false;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isAvailable() {
        return !booked;
    }

    public void book() {
        this.booked = true;
    }

    public void release() {
        this.booked = false;
    }

    public abstract SeatClass getSeatClass();

    public double getBaseFare() {
        return getSeatClass().getBaseFare();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Seat seat)) {
            return false;
        }
        return Objects.equals(seatNumber, seat.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNumber);
    }
}
