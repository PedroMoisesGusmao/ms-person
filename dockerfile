FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/person-entrypoint/target/*.jar app.jar

ENV DATABASE_URL=mongodb+srv://person:person@person.xchvu.mongodb.net/person
ENV PROJECT_PERSON_ZIPCODE_URL=http://viacep.com.br

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080