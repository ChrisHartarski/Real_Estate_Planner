# -------- Build stage --------
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY . .
RUN gradle clean build -x test

# -------- Run stage --------
FROM gcr.io/distroless/java21-debian12
WORKDIR /app

# Copy only the real Spring Boot runnable JAR
COPY --from=build /home/gradle/project/build/libs/Real_Estate_Planner.jar /app/app.jar

ENV PORT=8080

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]