# -------- Build stage --------
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY . .
RUN gradle clean build -x test

# -------- Run stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy ANY jar from build/libs
COPY --from=build /home/gradle/project/build/libs/*.jar /app/
RUN mv /app/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]