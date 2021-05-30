package com.example.redisbackend.impl;

import com.example.redisbackend.contract.RouteManagement;
import com.example.redisbackend.dto.FindRoute;
import com.example.redisbackend.dto.Route;
import com.google.gson.Gson;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class RouteManagementImpl implements RouteManagement {

    private Jedis jedis;

    public RouteManagementImpl(Jedis jedis) {
        this.jedis = jedis;
    }


    @Override
    public Set<Route> getAllRoutesFromAToB(String departure, String destination) {
        String strRouteFromAToB = "from " + departure + " to " + destination;
        Gson gson = new Gson();
        Set<String> smembers = jedis.smembers(strRouteFromAToB);
        Set<Route> routes = new HashSet();
        for (String json : smembers) {
            Route route = gson.fromJson(json, Route.class);
            routes.add(route);
        }
        return routes;
    }

    @Override
    public Route getCheapestRouteFromAToB(String aDestination, String bDestination) {
        return null;
    }

    @Override
    public Route getShortestRouteFromAtoB(String aDestination, String bDestination) {
        return null;
    }

    @Override
    public Route getLeastSwitchesOnRouteAtoB(String aDestination, String bDestination) {
        return null;
    }

    @Override
    public boolean addRouteCache(FindRoute findroute, Set<Route> route) {
        String origin = findroute.departure;
        String destination = findroute.destination;
        Gson gson = new Gson();
        String json = gson.toJson(route);
        String strRouteFromAToB = "from " + origin + " to " + destination;
        jedis.sadd(strRouteFromAToB, json);
        return true;
    }

}
