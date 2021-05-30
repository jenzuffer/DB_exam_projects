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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/route")
public class RouteController {
    private String host = "localhost";
    private int port = 6379;
    Jedis jedis = new Jedis(host, port);
    RouteManagementImpl routeManagement = new RouteManagementImpl(jedis);
    RestTemplate restTemplate = new RestTemplate();
    URI uri;



    @GetMapping("/allroutesto")
    public Set<Route> findAllRoutesTo(@RequestBody String bDestination) {
        Set<Route> allRoutesToB = routeManagement.getAllRoutesToB(bDestination);
        if (allRoutesToB.isEmpty()) {
            final String apiGatewayURL = "http://localhost:9081/routestodestination/" + bDestination;
            try {
                uri = new URI(apiGatewayURL);
                Set<Route> forEntity = (Set<Route>) restTemplate.postForObject(uri,bDestination, Route.class);
                System.out.println("forEntity: " + forEntity);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return allRoutesToB;
    }

    @GetMapping("/allroutesAtoB")
    public Set<Route> findAllROutesFromAToB(@RequestBody FindRoute findRoute) {
        Set<Route> allRoutesFromAToB = routeManagement.getAllRoutesFromAToB(findRoute.departure, findRoute.destination);
        if (allRoutesFromAToB.isEmpty()) {
            final String apiGatewayURL = "http://localhost:9081/allroutes/" + findRoute.departure + "/" + findRoute.destination;
            try {
                uri = new URI(apiGatewayURL);
                Set<Route> forEntity = (Set<Route>) restTemplate.postForObject(uri,findRoute, Route.class);
                routeManagement.addRouteCache(findRoute, forEntity);
                System.out.println("forEntity: " + forEntity);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return allRoutesFromAToB;
    }


    @PostMapping("/createBooking")
    public boolean createBooking(@RequestBody BookingDTO bookingDTO){
        FindRoute findRoute = new FindRoute(bookingDTO.getAirportDeparture(), bookingDTO.getAirportArrival());
        Set<Route> allROutesFromAToB = findAllROutesFromAToB(findRoute);
        if (!allROutesFromAToB.isEmpty()){
            final String apiGatewayURL = "http://localhost:9084/createbooking/";
            try {
                uri = new URI(apiGatewayURL);
                //allROutesFromAToB send somehow
                BookingDTO bookingDTO1 =  restTemplate.postForObject(uri, allROutesFromAToB, BookingDTO.class);

                return true;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
