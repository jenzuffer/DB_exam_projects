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
        Set<Route> routesreturn = new HashSet();
        Set<String> json = jedis.smembers(strRouteFromAToB);
        for (String str : json) {
            Route[] routes = gson.fromJson(str, Route[].class);
            for (Route route : routes) {
                routesreturn.add(route);
            }
        }
        return routesreturn;
    }

    @Override
    public Route getCheapestRouteFromAToB(String aDestination, String bDestination) {
        return null;
    }

    @Override
    public Route getShortestRouteFromAtoB(String departure, String destination) {
        String strRouteFromAToB = "shortest from " + departure + " to " + destination;
        Gson gson = new Gson();
        Set<String> json = jedis.smembers(strRouteFromAToB);
        Route route = null;
        for (String str : json) {
            Route[] routes = gson.fromJson(str, Route[].class);
            for (Route route1 : routes) {
                route = route1;
            }
        }
        return route;
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

    @Override
    public boolean addShortestRouteCache(FindRoute findroute, Route route) {
        String origin = findroute.departure;
        String destination = findroute.destination;
        Gson gson = new Gson();
        String json = gson.toJson(route);
        String strRouteFromAToB = "shortest from " + origin + " to " + destination;
        jedis.sadd(strRouteFromAToB, json);
        return true;
    }

}
