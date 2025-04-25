Create Docker container
```shell
docker run -d --name spshop_conteiner -e POSTGRES_DB=spshop -e POSTGRES_USER=[insert name] -e POSTGRES_PASSWORD=[insert password] -p 5432:5432 postgres:latest
```

заходим внутрь контейнера
```shell
docker exec -it spshop_conteiner psql -U postgres_spshop spshop
```

Это удалит контейнер и сам volume с базой данных:
```shell
docker-compose down -v
```
