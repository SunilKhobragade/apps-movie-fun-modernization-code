# Movie Fun!

## Build

### Build JAR

```bash
$ ./gradlew clean assemble
```

### Run Smoke Tests

```bash
./gradlew test
```

Smoke Tests require server running on port 8080 by default. To run against a custom URL run
```bash
$ MOVIE_FUN_URL=http://moviefun.example.com ./gradlew test
```

## Deploying to PCF

After running the build, deploy the application with
```bash
./gradlew deploy
```

Reset the entire PCF environment with
```bash
./gradlew resetPcfEnv
```

## Migrations

Create databases needed for local development with

```bash
mysql -uroot < databases/create_databases.sql
```

Run local migrations with

```bash
./gradlew flywayMigrate
```

Run migrations on the PCF environment which you're logged in to with

```bash
CF_MIGRATE=true ./gradlew cfMigrate
```


To see the circuit breaker working by defaulting to default method give different bucket name 

env:
      S3_ENDPOINTURL: http://s3.amazonaws.com
      S3_ACCESSKEY: AKIAJZQFBY46XGW2U3HA
      S3_SECRETKEY: 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
      S3_BUCKETNAME: cf-1defe0cf-6a41-4999-bb4f-3f1207b2c30bsunil ( diffrent bucket name to check the circuit breaker defaults to fallback method)