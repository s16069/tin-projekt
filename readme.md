# TIN Projekt

## Baza danych

```sh
$ docker run --rm --name s16069-postgres -e POSTGRES_USER=tin -e POSTGRES_PASSWORD=t4jn3_h4sl0 -e POSTGRES_DB=tin-db \
         -d -p 5432:5432 -v $PWD/pgdata:/var/lib/postgresql/data postgres
```

## Backend

```sh
$ cd backend
$ mvn spring-boot:run
```

## Frontend

```sh
$ cd frontend
$ npm install
$ npm run serve
```
