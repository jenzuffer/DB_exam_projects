package com.example.redisbackend.dto;


public class FindRoute {
    @Override
    public String toString() {
        return "FindRoute{" +
                "departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public FindRoute() {
    }

    public String departure;
    public String destination;

    public FindRoute(String departure, String destination) {
        this.departure = departure;
        this.destination = destination;
    }

}
