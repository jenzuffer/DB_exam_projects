neo4j is launched through docker using our docker-compose.yml file which you may execute with:
docker compose up

Neo4j receives its data from the 3 csv files. By launching neo4j you can visit 
http://localhost:7474/browser/ and import the csv files with the following cypher commands:

LOAD CSV WITH HEADERS FROM 'https://github.com/jenzuffer/DB_exam_projects/blob/main/neo4jBackend/
src/main/resources/airlines.csv' AS line FIELDTERMINATOR ';'
CREATE (:Airlines {code: line.CODE, name: line.NAME, country: line.COUNTRY})

