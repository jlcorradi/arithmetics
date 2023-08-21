FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app
ARG HOST_TIMEZONE=America/Sao_Paulo

COPY pom.xml .
COPY src ./src
RUN mvn -P \!docker clean package -DskipTests
FROM openjdk:17-oracle

WORKDIR /app

COPY --from=build /app/target/arithmetics.jar ./arithmetics.jar
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD ["java", "-jar", "-Duser.timezone=America/Sao_Paulo", "-Dspring.profiles.active=default", "arithmetics.jar"]
