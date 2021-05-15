package com.example.redisbackend.contract;

import com.example.redisbackend.dto.Route;

import java.util.Set;

public interface RouteManagement {
    Set<Route> getAllRoutesToB(String bDestination);
    Set<Route> getAllRoutesFromAToB(String aDestination, String bDestination);

    Route getCheapestRouteFromAToB(String aDestination, String bDestination);

    Route getShortestRouteFromAtoB(String aDestination, String bDestination);

    Route getLeastSwitchesOnRouteAtoB(String aDestination, String bDestination);

    boolean addRouteCache(Route route);
}
