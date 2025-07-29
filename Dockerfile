FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/Real_Estate_Planner-0.0.9.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]