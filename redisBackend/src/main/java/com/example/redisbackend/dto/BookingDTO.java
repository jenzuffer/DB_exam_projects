package com.example.redisbackend.dto;

public class BookingDTO {

    private int bookingCount;
    private String passport;
    private String airportDeparture;
    private String airportArrival;
    private String distance;
    private String time;


    public String getPassport() {
        return passport;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public String getAirportDeparture() {
        return airportDeparture;
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

    public BookingDTO(int bookingCount, String passport, String airportDeparture, String airportArrival, String distance, String time) {
        this.bookingCount = bookingCount;
        this.passport = passport;
        this.airportDeparture = airportDeparture;
        this.airportArrival = airportArrival;
        this.distance = distance;
        this.time = time;
    }

    public BookingDTO(int bookingCount, String passport, String airportDeparture, String airportArrival) {
        this.bookingCount = bookingCount;
        this.passport = passport;
        this.airportDeparture = airportDeparture;
        this.airportArrival = airportArrival;
    }
}
