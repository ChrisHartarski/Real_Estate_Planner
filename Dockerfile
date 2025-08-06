FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/Real_Estate_Planner.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]