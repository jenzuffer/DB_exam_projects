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
    public Set<Route> getAllRoutesToB(String bDestination) {
        String strTo = "to " + bDestination;
        Gson gson = new Gson();
        Set<String> smembers = jedis.smembers(strTo);
        Set<Route> routes = new HashSet();
        for (String json : smembers) {
            Route route = gson.fromJson(json, Route.class);
            routes.add(route);
        }
        return routes;
    }

    @Override
    public Set<Route> getAllRoutesFromAToB(String aDestination, String bDestination) {
        return null;
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
        String strFrom = "from " + origin;
        String strTo = "to " + destination;
        jedis.sadd(strFrom, json);
        jedis.sadd(strTo, json);
        return true;
    }

}
