package com.example.redisbackend.contract;

import com.example.redisbackend.dto.FindRoute;
import com.example.redisbackend.dto.Route;

import java.util.Set;

public interface RouteManagement {
    Set<Route> getAllRoutesFromAToB(String aDestination, String bDestination);

    Route getCheapestRouteFromAToB(String aDestination, String bDestination);

    Route getShortestRouteFromAtoB(String aDestination, String bDestination);

    Route getLeastSwitchesOnRouteAtoB(String aDestination, String bDestination);

    boolean addRouteCache(FindRoute findroute, Set<Route> route);
}
