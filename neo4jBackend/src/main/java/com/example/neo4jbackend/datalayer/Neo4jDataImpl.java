package com.example.neo4jbackend.datalayer;

import com.example.neo4jbackend.dto.Route;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

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
            Set<Route> routes = session.writeTransaction(new TransactionWork() {
                @Override
                public Set<Route> execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            return routes;
        }
    }

    public Set<Route> getRoutesFromAtoB(String start, String destination) {
        try (Session session = driver.session()) {
            Set<Route> routes = session.writeTransaction(new TransactionWork() {
                @Override
                public Set<Route> execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = start " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("start", start, "destination", destination));
                    return result.single().get(0).asString();
                }
            });
            return routes;
        }
    }
}
