FROM openjdk:17-jdk-alpine

RUN apk --no-cache add bash curl
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENV SERVER_PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-lookup:5432/lookup_db
ENV AXON_AXONSERVER_SERVERS=axonserver
ENV KEYCLOAK_URL=http://keycloak:8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=write"]