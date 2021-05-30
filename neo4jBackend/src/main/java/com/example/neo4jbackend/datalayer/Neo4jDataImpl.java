package com.example.neo4jbackend.datalayer;

import com.example.neo4jbackend.dto.FindRoute;
import com.example.neo4jbackend.dto.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;

import java.util.*;

public class Neo4jDataImpl {
    private static Driver driver = null;

    private static void closeDriver() {
        driver.close();
    }

    public Neo4jDataImpl() {

        String uri = "bolt://localhost:7687";
        if (driver == null) {
            driver = GraphDatabase.driver(uri, AuthTokens.basic("neo4j", "foobar"));
        }
    }

    public List<Route> getRoutesFromAtoB(FindRoute findRoute) {
        try (Session session = driver.session()) {
            List<Route> routes = (List<Route>) session.writeTransaction(new TransactionWork() {
                @Override
                public List<Route> execute(Transaction tx) {
                    List<Route> sets = new ArrayList<Route>();
                    Result result = tx.run("MATCH (source:Airport {id: 'URC'}), (target:Airport {id: 'SCO'})\n" +
                            "CALL gds.allShortestPaths.dijkstra.stream('myGraph1', {\n" +
                            "    sourceNode: id(source),\n" +
                            "    relationshipWeightProperty: 'distance'\n" +
                            "})\n" +
                            "YIELD index, sourceNode, targetNode, totalCost, nodeIds, costs\n" +
                            "RETURN\n" +
                            "    gds.util.asNode(sourceNode).name AS departure,\n" +
                            "    gds.util.asNode(targetNode).name AS destination,\n" +
                            "    totalCost,\n" +
                            "    [nodeId IN nodeIds | gds.util.asNode(nodeId).name] AS nodeNames,\n" +
                            "    costs\n" +
                            "ORDER BY costs", parameters("sourceAir", findRoute.departure, "destiAir", findRoute.destination));
                    List<Record> list = result.list();
                    for (Record record : list) {
                        ObjectMapper mapper = new ObjectMapper();
                        Route routeObject = mapper.convertValue(record.asMap() , Route.class);
                        sets.add(routeObject);
                    }
                    return sets;
                }
            });
            return routes;
        }
    }
}
