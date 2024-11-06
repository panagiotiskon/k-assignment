FROM openjdk:21-slim

LABEL authors = "panagiotis"

WORKDIR /app

COPY target/k-assignment-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]