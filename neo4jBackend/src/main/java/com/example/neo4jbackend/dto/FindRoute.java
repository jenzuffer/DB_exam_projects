package com.example.neo4jbackend.dto;

import java.util.Objects;

public class FindRoute {

    @Override
    public String toString() {
        return "FindRoute{" +
                "departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public String departure;
    public String destination;

    public FindRoute(String departure, String destination) {
        this.departure = departure;
        this.destination = destination;
    }


}
