# camunda-tech-challenge

A simple Spring Boot microservice that fetches random animal pictures (cat, dog, duck) from external providers, stores them in a database, and lets you retrieve the last saved picture for a given animal type.
This assumes that the used APIs will work. 

## Requirements

- Java 21
- Maven (or use the included Maven Wrapper — no local Maven install required)
- Docker (optional, for containerized runs)

## Running the application

The project includes the Maven Wrapper, so you don't need Maven installed locally.

```bash
./mvnw spring-boot:run
```

Or build a jar and run it directly:

```bash
./mvnw clean package
java -jar target/techchallenge-0.0.1-SNAPSHOT.jar
```

The service starts on [http://localhost:8080](http://localhost:8080) and uses an in-memory H2 database, so no external database setup is required. Please note that there won't be any pictures saved the first time you run it, so don't forget to fetch an animal picture first. 

## Web UI

A minimal UI is available at the root of the application once it's running:

[http://localhost:8080/index.html](http://localhost:8080/index.html)

From there you can pick an animal type (cat, dog, or duck), fetch a number of new pictures, and view the last one that was saved.

## API documentation (Swagger)

The REST API is documented via springdoc-openapi. Once the application is running, you can explore and try out the endpoints using Swagger UI:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI spec (JSON): [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Running the tests

Unit tests are run with:

```bash
./mvnw test
```

## Building and running with Docker

The project includes a multi-stage `Dockerfile` that builds the jar with Maven and packages it into a lightweight JRE image.

Build the image:

```bash
docker build -t camunda-tech-challenge .
```

Run the container:

```bash
docker run -p 8080:8080 camunda-tech-challenge
```

The service will then be available at [http://localhost:8080](http://localhost:8080), with the UI and Swagger docs reachable at the same paths described above.

## Known limitations / Things to improve

- **No global error handling**: there's no exception handler, so failures are thrown as raw 500s instead of 4xx responses. For example, an unsupported `animalType` throws `IllegalArgumentException`, and calling `GET /api/pictures/{animalType}/last` before any picture has been saved returns `null`.

- **No request validation**: `count` in `POST /api/pictures` has no lower/upper bound, so a large or negative value can trigger a long-running fetch loop. `animalType` is matched case-sensitively against the provider map with no enum or normalization.

- **In-memory H2 database**: all saved pictures are lost on every restart; there's no persistent database option.

- **Hardcoded provider URLs**: external API endpoints (e.g. `https://cataas.com/cat`) are hardcoded in each provider instead of being externalized via an application property.

- **Test coverage gaps**: existing unit tests cover the happy path; there's no coverage for the error/edge cases above (invalid animal type, missing picture, provider failures).

- **No way to browse saved pictures**: `fetchAndSave` can save several pictures at once, but the only read endpoint is `GET /api/pictures/{animalType}/last`, which returns just the most recently saved one. There's no endpoint to list all pictures, or fetch a specific picture by id.

---

🐱🐶🦆 *Looking forward to meeting you in the next interview!*