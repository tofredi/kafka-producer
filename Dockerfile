FROM maven:3.6.3-jdk-11-slim AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package


FROM openjdk:11-jre-slim
COPY --from=builder /app/target/kafka-producer-0.0.1-SNAPSHOT.jar app/kafka-producer-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "app/kafka-producer-0.0.1-SNAPSHOT.jar"]

