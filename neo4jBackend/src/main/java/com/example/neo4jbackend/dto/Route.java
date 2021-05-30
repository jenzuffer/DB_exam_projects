package com.example.neo4jbackend.dto;

import java.util.List;

public class Route {
    public float totalCost;
    public String flight_duration;
    public String destination;
    public String departure;
    public List<String> nodeNames;
    public List<String> costs;

    public Route(float totalCost, String flight_duration, String destination, String departure, List<String> nodeNames, List<String> costs) {
        this.totalCost = totalCost;
        this.flight_duration = flight_duration;
        this.destination = destination;
        this.departure = departure;
        this.nodeNames = nodeNames;
        this.costs = costs;
    }

    public Route() {
    }

    public Route setCosts(List<String> costs) {
        this.costs = costs;
        return this;
    }

    public Route setTotalCost(float totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public Route setFlight_duration(String flight_duration) {
        this.flight_duration = flight_duration;
        return this;
    }

    public Route setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Route setDeparture(String departure) {
        this.departure = departure;
        return this;
    }

    @Override
    public String toString() {
        return "Route{" +
                "totalCost=" + totalCost +
                ", flight_duration='" + flight_duration + '\'' +
                ", destination='" + destination + '\'' +
                ", departure='" + departure + '\'' +
                ", nodeNames=" + nodeNames +
                '}';
    }

    public Route setNodeNames(List<String> nodeNames) {
        this.nodeNames = nodeNames;
        return this;
    }
}
