package com.example.redisbackend.rest;


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


    @PostMapping("")
    public FindRoute test(@RequestBody FindRoute findRoute) throws URISyntaxException {
        String temp = "http://localhost:9081/neo4j";
        uri = new URI(temp);
        FindRoute hey = restTemplate.postForObject(uri,findRoute,FindRoute.class);
        return null;
       /* System.out.println(findRoute);
        List mylist = new ArrayList<Route>();
        mylist.add(new Route(1f,1f,"he","he","he",1));
        mylist.add(new Route(1f,1f,"hef","fhe","hef",1));
        Set<Route> set = Set.copyOf(mylist);
        return set; */
    }

    @GetMapping("/allroutesto}")
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

    @GetMapping("/allroutesAtoB}")
    public Set<Route> findAllROutesFromAToB(@RequestBody FindRoute findRoute) {
        Set<Route> allRoutesFromAToB = routeManagement.getAllRoutesFromAToB(findRoute.departure, findRoute.destination);
        if (allRoutesFromAToB.isEmpty()) {
            final String apiGatewayURL = "http://localhost:9081/allroutes/" + findRoute.departure + "/" + findRoute.destination;
            try {
                uri = new URI(apiGatewayURL);
                Set<Route> forEntity = (Set<Route>) restTemplate.postForObject(uri,findRoute, Route.class);
                System.out.println("forEntity: " + forEntity);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return allRoutesFromAToB;
    }



    //what is this going to be used for ?
    @PostMapping("/updateroutecache")
    public boolean addRouteCache(@RequestBody Route route) {
        return routeManagement.addRouteCache(route);
    }
}
