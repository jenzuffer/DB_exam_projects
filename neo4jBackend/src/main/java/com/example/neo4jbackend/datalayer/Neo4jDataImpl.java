package com.example.neo4jbackend.datalayer;

import com.example.neo4jbackend.dto.Route;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

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
            driver = GraphDatabase.driver(uri);
        }
    }

    public Set<Route> getRoutesToDestination(String destination) {
        try (Session session = driver.session()) {
            Set<Route> routes = (Set<Route>)session.writeTransaction(new TransactionWork() {
                @Override
                public Set<Route> execute(Transaction tx) {
                    HashSet<Route> sets = new HashSet<>();
                    Result result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", destination));
                    return sets;
                }
            });
            return routes;
        }
    }

    public Set<Route> getRoutesFromAtoB(String start, String destination) {
        try (Session session = driver.session()) {
            Set<Route> routes = (Set<Route>) session.writeTransaction(new TransactionWork() {
                @Override
                public Set<Route> execute(Transaction tx) {
                    HashSet<Route> sets = new HashSet<>();
                    Result result = tx.run("MATCH (source:Airport {id: '$sourceAir'}), (target:Airport {id: '$destiAir'})\n" +
                            "CALL gds.beta.allShortestPaths.dijkstra.stream('myGraph1', {\n" +
                            "    sourceNode: id(source),\n" +
                            "    relationshipWeightProperty: 'distance'\n" +
                            "})\n" +
                            "YIELD index, sourceNode, targetNode, totalCost, nodeIds, costs\n" +
                            "RETURN\n" +
                            "    index,\n" +
                            "    gds.util.asNode(sourceNode).name AS sourceNodeName,\n" +
                            "    gds.util.asNode(targetNode).name AS targetNodeName,\n" +
                            "    totalCost,\n" +
                            "    [nodeId IN nodeIds | gds.util.asNode(nodeId).name] AS nodeNames,\n" +
                            "    costs\n" +
                            "ORDER BY costs", parameters("sourceAir", start, "destiAir", destination));
                    List<Record> list = result.list();
                    for (Record record : list){
                        System.out.println("");
                    }
                return sets;
                }
            });
            return routes;
        }
    }
}
