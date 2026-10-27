FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /build/target/atlas-rh-0.0.1-SNAPSHOT.jar app.jar
COPY DB.proprieties ./DB.proprieties

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
