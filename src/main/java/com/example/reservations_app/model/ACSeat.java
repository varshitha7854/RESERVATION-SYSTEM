package com.example.reservations_app.model;

public class ACSeat extends Seat {

    private SeatClass acClass;

    public ACSeat() {
    }

    public ACSeat(String seatNumber, SeatClass acClass) {
        super(seatNumber);
        setAcClass(acClass);
    }

    public SeatClass getAcClass() {
        return acClass;
    }

    public void setAcClass(SeatClass acClass) {
        if (acClass == null || !acClass.isAC()) {
            throw new IllegalArgumentException("AC seat requires an AC class.");
        }
        this.acClass = acClass;
    }

    @Override
    public SeatClass getSeatClass() {
        return acClass;
    }
}
