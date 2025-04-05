# discount-service

> A robust foundation for a flexible and scalable discount management service.

- Supports Multiple Discount Policies Per Product
- Implements Priority Handling for Discount Policies
- Offers Flexible Discount Rules
- Adaptable to Changing Business Needs

## API Documentation
```
http://localhost:8080/swagger-ui.html
```

## Database Schema
> Database schema and local data are provided by liquibase db change management tool
> 
> Database migration scripts are kept in [changesets](./src/main/resources/db/changesets) subdirectory

## Run on local enviroment
1.   Docker compose is kept in [docker](./docker/postgres) subdirectory
```bash
docker compose up
```
2.  Gradle wrapper script is kept in [root](./gradlew) subdirectory
```bash
./gradlew clean bootRun
```


## Build
```bash
./gradlew clean build
```

## Test
```bash
./gradlew clean test
```

## Configuration
- Default service configuration is kept within [application.yaml](src/main/resources/application.yaml) file.
- Test service configuration is kept within [application-test.yaml](src/test/resources/application-test.yaml) file.

