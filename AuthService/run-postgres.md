
docker run -d --name spshop_conteiner -e POSTGRES_DB=spshop -e POSTGRES_USER=[insert name] -e POSTGRES_PASSWORD=[insert password] -p 5432:5432 postgres:latest

заходим внутрь контейнера
docker exec -it spshop_conteiner psql -U postgres_spshop spshop

