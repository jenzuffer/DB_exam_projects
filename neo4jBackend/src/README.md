neo4j is launched through docker using our docker-compose.yml file which you may execute with:
docker compose up

Neo4j receives its data from the 3 csv files. By launching neo4j you can visit 
http://localhost:7474/browser/ and import the csv files with the following cypher commands:

LOAD CSV WITH HEADERS FROM 'https://raw.githubusercontent.com/jenzuffer/DB_exam_projects/main/neo4jBackend/src/main/resources/airlines.csv' AS airlines FIELDTERMINATOR ';' 
CREATE (B:Airline {code: airlines.CODE, name: airlines.NAME, country: airlines.COUNTRY})


example query:
MATCH (n:Airline)
WHERE n.code = 'E7' XOR n.code = 'PE'
return n.code, n.country, n.name


Create CONSTRAINT ON (a:Airport) ASSERT a.id IS UNIQUE;
LOAD CSV WITH HEADERS FROM 'https://raw.githubusercontent.com/jenzuffer/DB_exam_projects/main/neo4jBackend/src/main/resources/airports.csv' AS airports FIELDTERMINATOR ';'
CREATE(airport:Airport {id:airports.CODE, name: airports.NAME, city: airports.CITY, country: airports.COUNTRY, latitude:toFloat(airports.LATITUDE),
longitude:toFloat(airports.LONGITUDE)});


example query:
MATCH (n:Airport)
WHERE n.code = 'YUT' XOR n.code = 'YVV'
return n.code, n.country, n.city, n.name


LOAD CSV WITH HEADERS FROM 'https://raw.githubusercontent.com/jenzuffer/DB_exam_projects/main/neo4jBackend/src/main/resources/routes.csv' AS routes FIELDTERMINATOR ';' 
CREATE (R:Route {airline_code: routes.AIRLINE_CODE, departure : routes.SOURCE_CODE, arrival: routes.DESTINATION_CODE, distance:toFloat(routes.DISTANCE), 
time:toFloat(routes.TIME)})

example query:
MATCH (n:Route)
WHERE n.destination_code = 'KZN' XOR n.destination_code = 'UUA'
return n.airline_code, n.source_code, n.destination_code, n.distance























build relationship between airports and routes:

Match (a:Route)
match (b:Airport)
match (c:Airport)
where a.departure = b.id and a.arrival = c.id
CREATE (b)-[:COMES_FROM {distance: a.distance}]->(a)
CREATE (a)-[:GOES_TO {distance: a.distance}]->(c)



CALL gds.graph.create(
    'myGraph',
    '*',
    ['COMES_FROM', 'GOES_TO'],
    {
        relationshipProperties: ['distance'],
        nodeProperties: ['latitude', 'longitude']
    }
)

//not working
CALL gds.graph.create(
    'myGraph',
    ['Airport', 'Route'],
    ['COMES_FROM', 'GOES_TO'],
    {
        relationshipProperties: 'distance',
        nodeProperties: ['latitude', 'longitude']
    }
)



//The following will estimate the memory requirements for running the algorithm in write mode:
MATCH (source:Airport {id: 'OKA'}), (target:Airport {id: 'KIX'})
CALL gds.beta.shortestPath.astar.write.estimate('myGraph', {
    sourceNode: id(source),
    targetNode: id(target),
    latitudeProperty: 'latitude',
    longitudeProperty: 'longitude',
    writeRelationshipType: 'PATH'
})
YIELD nodeCount, relationshipCount, bytesMin, bytesMax, requiredMemory
RETURN nodeCount, relationshipCount, bytesMin, bytesMax, requiredMemory



//The following will run the algorithm and stream results:
MATCH (source:Airport {id: 'OKA'}), (target:Airport {id: 'KIX'})
CALL gds.beta.shortestPath.astar.stream('myGraph', {
    sourceNode: id(source),
    targetNode: id(target),
    latitudeProperty: 'latitude',
    longitudeProperty: 'longitude',
    relationshipWeightProperty: 'distance'
})
YIELD index, sourceNode, targetNode, totalCost, nodeIds, costs
RETURN
    index,
    gds.util.asNode(sourceNode).name AS sourceNodeName,
    gds.util.asNode(targetNode).name AS targetNodeName,
    totalCost,
    [nodeId IN nodeIds | gds.util.asNode(nodeId).name] AS nodeNames,
    costs
ORDER BY index

//relationship which selects a specific airport to find all routes comming from it
MATCH (p:Airport {id: 'OKA'})-[r]->(route)
RETURN p, route


//dijstrka graph
CALL gds.graph.create(
    'myGraph1',
    ['Route', 'Airport'],
    ['*'],
    {
        relationshipProperties: 'distance'
    }
)


//dijsktra search
MATCH (source:Airport {id: 'OKA'})
CALL gds.beta.allShortestPaths.dijkstra.stream('myGraph1', {
    sourceNode: id(source),
    relationshipWeightProperty: 'distance'
})
YIELD index, sourceNode, targetNode, totalCost, nodeIds, costs
RETURN
    index,
    gds.util.asNode(sourceNode).name AS sourceNodeName,
    gds.util.asNode(targetNode).name AS targetNodeName,
    totalCost,
    [nodeId IN nodeIds | gds.util.asNode(nodeId).name] AS nodeNames,
    costs
ORDER BY index


delete everything:
MATCH (n)
DETACH DELETE n

//delete graph:
CALL gds.graph.drop('myGraph')



//ny
