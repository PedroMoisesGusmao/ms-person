FROM maven:3.9.9-amazoncorretto-17-debian as build

RUN mvn package

FROM amazoncorretto:8-alpine3.21-jdk

WORKDIR /app

COPY --from=build /target/person-entrypoint-0.0.1-SNAPSHOT.jar app/person.jar

ENTRYPOINT [ "java", "-jar", "person.jar" ]