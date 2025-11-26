# -------- Build stage --------
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY . .
RUN gradle clean build -x test

# -------- Run stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the Gradle container
COPY --from=build /home/gradle/project/build/libs/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]