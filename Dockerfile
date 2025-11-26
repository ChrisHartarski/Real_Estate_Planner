# -------- Build stage --------
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY . .
RUN gradle clean build -x test

# -------- Run stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy only the real Spring Boot runnable JAR
COPY --from=build /home/gradle/project/build/libs/Real_Estate_Planner.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]