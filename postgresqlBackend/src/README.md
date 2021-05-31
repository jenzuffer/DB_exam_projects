postgresql is launched through docker using our docker-compose.yml file which you 
may execute with:
docker compose up


POSTgres specific queries:

//create table
create table if not exists booking_information (
	bookintcount int not null,
	passport text not null primary key,
	departure text not null,
	destination text not null,
	airportnames text[] not null
)
