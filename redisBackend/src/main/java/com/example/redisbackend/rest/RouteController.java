package com.example.redisbackend.rest;


import com.example.redisbackend.dto.BookingDTO;
import com.example.redisbackend.dto.FindRoute;
import com.example.redisbackend.dto.Route;
import com.example.redisbackend.impl.RouteManagementImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.util.*;

@RestController
@RequestMapping("/route")
public class RouteController {
    private String host = "localhost";
    private int port = 6379;
    Jedis jedis = new Jedis(host, port);
    RouteManagementImpl routeManagement = new RouteManagementImpl(jedis);
    RestTemplate restTemplate = new RestTemplate();
    URI uri;


    @GetMapping("/allroutesAtoB")
    public Set<Route> findAllROutesFromAToB(@RequestBody FindRoute findRoute) {
        Set<Route> allRoutesFromAToB = routeManagement.getAllRoutesFromAToB(findRoute.departure, findRoute.destination);
        if (allRoutesFromAToB.isEmpty()) {
            final String apiGatewayURL = "http://localhost:9081/neo4j/allroutes/";
            try {
                uri = new URI(apiGatewayURL);
                Route[] routes = restTemplate.postForObject(uri, findRoute, Route[].class);
                for (Route route : routes) {
                    allRoutesFromAToB.add(route);
                }
                routeManagement.addRouteCache(findRoute, allRoutesFromAToB);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return allRoutesFromAToB;
    }

    @GetMapping("/shortestRouteAToB")
    public Route findShortestRouteAToB(@RequestBody FindRoute findRoute) {
        Route shortestRouteFromAtoB = routeManagement.getShortestRouteFromAtoB(findRoute.departure, findRoute.destination);
        if (shortestRouteFromAtoB == null) {
            final String apiGatewayURL = "http://localhost:9081/neo4j/shortestroute/";
            try {
                uri = new URI(apiGatewayURL);
                Route routes = restTemplate.postForObject(uri, findRoute, Route.class);
                shortestRouteFromAtoB = routes;
                routeManagement.addShortestRouteCache(findRoute, shortestRouteFromAtoB);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return shortestRouteFromAtoB;
    }


    @PostMapping("/createbooking")
    public boolean createBooking(@RequestBody BookingDTO bookingDTO) {
        boolean answer = true;
        final String apiGatewayURL = "http://localhost:9084/booking/createbooking/";
        try {
            uri = new URI(apiGatewayURL);
            answer = restTemplate.postForObject(uri, bookingDTO, boolean.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
