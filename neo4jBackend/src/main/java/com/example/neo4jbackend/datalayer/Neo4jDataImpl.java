package com.example.neo4jbackend.datalayer;

import com.example.neo4jbackend.dto.Route;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import java.util.Map;
import java.util.Set;

public class Neo4jDataImpl {

    public Neo4jDataImpl() {
        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder().build();
        GraphDatabaseService db = managementService.database(DEFAULT_DATABASE_NAME);

        try (Transaction tx = db.beginTx();
             Result result = tx.execute("MATCH (n {name: 'my node'}) RETURN n, n.name")) {
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                for (Map.Entry<String, Object> column : row.entrySet()) {
                    rows += column.getKey() + ": " + column.getValue() + "; ";
                }
                rows += "\n";
            }
        }
    }

    public Set<Route> getRoutesToDestination(String destination) {

    }
}
