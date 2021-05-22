neo4j is launched through docker using our docker-compose.yml file which you may execute with:
docker compose up

Neo4j receives its data from the 3 csv files. By launching neo4j you can visit 
http://localhost:7474/browser/ and import the csv files with the following cypher commands:

LOAD CSV FROM 'https://raw.githubusercontent.com/jenzuffer/DB_exam_projects/main/neo4jBackend/src/main/resources/airlines.csv' AS line FIELDTERMINATOR ';' 
CREATE (:Airlines {code: line[0], name: line[1], country: line[2]})

example query:
MATCH (n:Airlines)
WHERE n.code = 'E7' XOR n.code = 'PE'
return n.code, n.country, n.name


LOAD CSV FROM 'https://raw.githubusercontent.com/jenzuffer/DB_exam_projects/main/neo4jBackend/src/main/resources/airports.csv' AS line FIELDTERMINATOR ';' 
CREATE (:Airports {code: line[0], name: line[1], city: line[2], country: line[3], lattitude: line[4],
longtitude: line[5]})

example query:
MATCH (n:Airports)
WHERE n.code = 'YUT' XOR n.code = 'YVV'
return n.code, n.country, n.city, n.name


LOAD CSV FROM 'https://raw.githubusercontent.com/jenzuffer/DB_exam_projects/main/neo4jBackend/src/main/resources/routes.csv' AS line FIELDTERMINATOR ';' 
CREATE (:Routes {airline_code: line[0], source_code: line[1], destination_code: line[2], distance: line[3], 
time: line[4]})

example query:
MATCH (n:Routes)
WHERE n.destination_code = 'KZN' XOR n.destination_code = 'UUA'
return n.airline_code, n.source_code, n.destination_code, n.distance

//experimental below


CREATE INDEX FOR (n:Routes) ON (n.source_code)
CREATE INDEX FOR (n:Routes) ON (n.destination_code)
CREATE INDEX FOR (n:Airports) ON (n.code)

MATCH (n:Routes {source_code:'RKV', destination_code: 'WWK'}), (a:Airports {code:'WWK'})
CREATE (n)-[:HAS_ROUTE]->(a)







build relationship between airports and routes:
MATCH
  (a:Routes),
  (b:Airports),
  (c:Airports)
WHERE a.source_code = b.code AND a.destination_code = c.code
CREATE (b)-[r:RELTYPE {relation: a.source_code + ' route with airport: ' + b.code}]->(a)
CREATE (a)-[r1:RELTYPE {relation: a.destination_code + ' route with airport: ' + c.code}]->(c)
RETURN type(r), r.source_code, r1.destination_code




Match (a:Routes)
match (b:Airports)
match (c:Airports)
where a.source_code = b.code and a.destination_code = c.code
merge (b)-[:relation {distance: a.distance}]->(a)
merge (a)-[:relation {distance: a.distance}]->(c)
return type(r), b, c


CALL gds.graph.create(
    'myGraph1',
    'Routes',
    'relation',
    {
        relationshipProperties: 'distance'
    }
)

MATCH (source:Routes {source_code: 'RKV'}), (target:Routes {destination_code: 'WWK'})
CALL gds.beta.shortestPath.dijkstra.write.estimate('myGraph', {
    sourceNode: id(source),
    targetNode: id(target),
    relationshipWeightProperty: 'distance',
    writeRelationshipType: 'PATH'
})
YIELD nodeCount, relationshipCount, bytesMin, bytesMax, requiredMemory
RETURN nodeCount, relationshipCount, bytesMin, bytesMax, requiredMemory


delete everything:
MATCH (n)
DETACH DELETE n