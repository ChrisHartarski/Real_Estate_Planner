FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/Real_Estate_Planner-0.0.9.jar"]