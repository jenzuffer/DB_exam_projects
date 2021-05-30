redis is launched through docker using our docker-compose.yml file which you may execute with:
docker compose up

launch redis client with:
docker exec -it redis_db redis-cli


Postman Queries:
url: http://localhost:9083/route/allroutesAtoB/
method: get
json body:
{
    "departure": "OKA",
    "destination": "SCO"
}

url: http://localhost:9083/route/shortestRouteAToB/
method: get
json bdoy:
{
    "departure": "OKA",
    "destination": "SCO"
}