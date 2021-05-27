package com.example.neo4jbackend.dto;

public class Route {
    public float kilometer_distance;
    public String flight_duration;
    public String destination;
    public String origin;
    public String airline_code;

    public Route(float kilometer_distance, String flight_duration,
                 String destination, String origin, String flight_switches) {
        this.kilometer_distance = kilometer_distance;
        this.flight_duration = flight_duration;
        this.destination = destination;
        this.origin = origin;
        this.airline_code = flight_switches;
    }
}
