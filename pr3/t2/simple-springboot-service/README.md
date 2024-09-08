# Java App

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
    --rm -e DB_NAME=db \
    -e DB_USERNAME=root \
    -e DB_PASSWORD=root \
    -e DB_PORT=5342 \
    -p 8080:8080 \
    --network host \
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
