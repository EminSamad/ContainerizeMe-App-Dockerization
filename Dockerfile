FROM eclipse-temurin:23 AS build
WORKDIR /app
COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew -x test bootJar

FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

HEALTHCHECK --interval=30s --timeout=3s --start-period=10s CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app/app.jar"]