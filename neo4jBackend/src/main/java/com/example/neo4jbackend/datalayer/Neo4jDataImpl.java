package com.example.neo4jbackend.datalayer;

import com.example.neo4jbackend.dto.FindRoute;
import com.example.neo4jbackend.dto.Route;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public Set<Route> getRoutesFromAtoB(FindRoute findRoute) {
        try (Session session = driver.session()) {
            Set<Route> routes = (Set<Route>) session.writeTransaction(new TransactionWork() {
                @Override
                public Set<Route> execute(Transaction tx) {
                    HashSet<Route> sets = new HashSet<>();
                    Result result = tx.run("MATCH (source:Airport {id: 'OKA'}), (target:Airport {id: 'SCO'})\n" +
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
                        Route record1 = (Route) record;
                        System.out.println(record1);
                        /*
                        for (Map.Entry<String, Object> strobject : stringObjectMap.entrySet()) {

                            System.out.println("strobject.getKey(): " + strobject.getKey());
                            System.out.println("strobject.getValue(): " + strobject.getValue());
                        }*/
                    }
                    return sets;
                }
            });
            return routes;
        }
    }
}
