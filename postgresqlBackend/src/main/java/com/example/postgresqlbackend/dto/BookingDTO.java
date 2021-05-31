package com.example.postgresqlbackend.dto;

import java.util.List;

public class BookingDTO {


    private int bookingCount;
    private String passport;
    private String airportDeparture;
    private String airportArrival;
    private String distance;
    private String time;
    private List<String> airportNames;
    private List<String> costs;

    public String getPassport() {
        return passport;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public String getAirportDeparture() {
        return airportDeparture;
    }

    public List<String> getAirportNames() {
        return airportNames;
    }

    public String getAirportArrival() {
        return airportArrival;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }

    public BookingDTO(int bookingCount, String passport, String airportDeparture, String airportArrival, List<String> airportNames) {
        this.bookingCount = bookingCount;
        this.passport = passport;
        this.airportDeparture = airportDeparture;
        this.airportArrival = airportArrival;
        this.airportNames = airportNames;
    }
}
