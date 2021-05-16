package com.example.neo4jbackend.dto;

public class Route {
    public float kilometer_distance;
    public float price;
    public String flight_duration;
    public String destination;
    public String origin;
    public int flight_switches;

    public Route(float kilometer_distance, float price, String flight_duration,
                 String destination, String origin, int flight_switches) {
        this.kilometer_distance = kilometer_distance;
        this.price = price;
        this.flight_duration = flight_duration;
        this.destination = destination;
        this.origin = origin;
        this.flight_switches = flight_switches;
    }
}
