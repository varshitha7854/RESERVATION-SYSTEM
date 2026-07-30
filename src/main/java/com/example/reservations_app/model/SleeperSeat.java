package com.example.reservations_app.model;

public class SleeperSeat extends Seat {

    public SleeperSeat() {
    }

    public SleeperSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public SeatClass getSeatClass() {
        return SeatClass.SLEEPER;
    }
}
