package com.example.neo4jbackend.rest;

import com.example.neo4jbackend.datalayer.Neo4jDataImpl;
import com.example.neo4jbackend.dto.FindRoute;
import com.example.neo4jbackend.dto.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

    private Neo4jDataImpl neo4jDataimpl = new Neo4jDataImpl();
    private RestTemplate restTemplate = new RestTemplate();
    private URI uri;

    @PostMapping("")
    @ResponseBody
    public FindRoute test(@RequestBody FindRoute findRoute) {
        return new FindRoute("no", "bæ");
    }


    @GetMapping("/routestodestination/")
    @ResponseBody
    public Set<Route> getRoutesToDestination(@RequestBody String bDestination) {
        //check neo4j for route og opdater så redis cache
        Set<Route> routes = neo4jDataimpl.getRoutesToDestination(bDestination);
        return routes;
    }

    @GetMapping("/allroutes/")
    @ResponseBody
    public Set<Route> getRoutesfromAtoB(@RequestBody FindRoute findRoute) {
        return neo4jDataimpl.getRoutesFromAtoB(findRoute);
    }
}
