package com.example.reservations_app.model;

import java.util.Arrays;

public enum SeatClass {
    SLEEPER("Sleeper", 250.0),
    AC_3_TIER("AC 3-Tier", 750.0),
    AC_2_TIER("AC 2-Tier", 1000.0),
    AC_FIRST("AC First Class", 1500.0);

    private final String label;
    private final double baseFare;

    SeatClass(String label, double baseFare) {
        this.label = label;
        this.baseFare = baseFare;
    }

    public String getLabel() {
        return label;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public boolean isAC() {
        return this != SLEEPER;
    }

    public static SeatClass fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Seat class is required.");
        }

        String normalized = value.trim()
                .replace("-", "_")
                .replace(" ", "_")
                .toUpperCase();

        return Arrays.stream(values())
                .filter(seatClass -> seatClass.name().equals(normalized)
                        || seatClass.label.equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid seat class: " + value));
    }
}
