# Java App

## Run app tests

```bash
curl -X POST 'http://localhost:8080/addElement?name=foo&description=bar'
curl -X POST 'http://localhost:8080/addElement?name=%D0%A2%D0%B5%D1%85%D0%BD%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8%D0%B8%20%D0%92%D0%B8%D1%80%D1%82%D1%83%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%B8%20%D0%BA%D0%BB%D0%B8%D0%B5%D0%BD%D1%82-%D1%81%D0%B5%D1%80%D0%B2%D0%B5%D1%80%D0%BD%D1%8B%D1%85%20%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B9&description=%D0%94%D0%B8%D1%81%D1%86%D0%B8%D0%BF%D0%BB%D0%B8%D0%BD%D0%B0%2C%20%D0%B2%20%D0%BA%D0%BE%D1%82%D0%BE%D1%80%D0%BE%D0%B9%20%D1%80%D0%B0%D0%B7%D0%B1%D0%B8%D1%80%D0%B0%D1%8E%D1%82%D1%81%D1%8F%20%D0%B2%D0%BE%D0%BF%D1%80%D0%BE%D1%81%D1%8B%20%D1%80%D0%B0%D0%B7%D0%B2%D0%B5%D1%80%D1%82%D1%8B%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F%2C%20%D0%BF%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%BA%D0%B8%20%D0%B8%20%D1%8D%D0%BA%D1%81%D0%BF%D0%BB%D1%83%D0%B0%D1%82%D0%B0%D1%86%D0%B8%D0%B8%20%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%20%D0%BE%D0%B1%D0%B5%D1%81%D0%BF%D0%B5%D1%87%D0%B5%D0%BD%D0%B8%D1%8F'
```

```bash
curl -X GET 'http://localhost:8080/elements'
```

output should be as follows:
```json
[{"id":1,"name":"foo","description":"bar"},{"id":2,"name":"Технологии Виртуализации клиент-серверных приложений","description":"Дисциплина, в которой разбираются вопросы развертывания, поставки и эксплуатации программного обеспечения"}]
```

## Docker

### Create network

```sh
docker network create pr3-bridge
```

### Build base image (for ONBUILD to work)

```sh
docker build . -t pr3-java-service-base:0.0.1 -f Dockerfile.base
```

### Build application image

```sh
docker build . -t pr3-java-service-app:0.0.1 -f Dockerfile.child
```

### Run docker image
```sh
docker run \
    --name pr3-java-service \
    -e DB_NAME=db \
    -e DB_USERNAME=root \
    -e DB_PASSWORD=root \
    -e DB_PORT=5432 \
    -p 8080:8080 \
    --network host \
    -d \
    pr3-java-service-app:0.0.1
```

## Maven

### Run application

env:

```bash
DB_USERNAME=root DB_PASSWORD=root DB_NAME=db DB_PORT=5432 mvn spring-boot:run
```

or:
```sh
mvn package && java -jar ./target/app.jar
```

### download and compile the project dependencies specified in `pom.xml`

```bash
mvn compile
```

### Build project

```bash
mvn package
```

Build artifact is *./target/simple-springboot-service-1.0-SNAPSHOT.jar*


## Spin up Postgres

```bash
sudo docker run \
    --name p3_t2_postgres \
    -e POSTGRES_USER=root \
    -e POSTGRES_PASSWORD=root \
    -e POSTGRES_DB=db \
    -p 5432:5432 \
    --network host \
    -d \
    postgres
```

debug:
```bash
psql -d db
```
