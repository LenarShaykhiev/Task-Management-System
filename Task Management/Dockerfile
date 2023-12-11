FROM maven:3.9.5-amazoncorretto-17 AS build
COPY src /home/application/src
COPY pom.xml /home/application/
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/application/pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=build /home/application/target/application.jar /usr/local/lib/application.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/usr/local/lib/application.jar"]
