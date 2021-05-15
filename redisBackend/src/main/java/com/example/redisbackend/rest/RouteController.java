package com.example.redisbackend.rest;


import com.example.redisbackend.dto.Route;
import com.example.redisbackend.impl.RouteManagementImpl;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Set;

@RestController
@RequestMapping("/route")
public class RouteController {
    private String host = "localhost";
    private int port = 6379;
    Jedis jedis = new Jedis(host, port);
    RouteManagementImpl routeManagement = new RouteManagementImpl(jedis);

    @GetMapping("/allroutesto}")
    public Set<Route> findAllRoutesTo(@PathVariable String bDestination) {
        return routeManagement.getAllRoutesToB(bDestination);
    }

    @PostMapping("/updateroutecache")
    public boolean addRouteCache(@RequestBody Route route) {
        return routeManagement.addRouteCache(route);
    }
}
