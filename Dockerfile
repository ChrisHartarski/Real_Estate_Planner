# -------- Build stage --------
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY . .
RUN gradle clean build -x test

RUN echo "---- BUILT FILES ----" && ls -al /home/gradle/project/build/libs

# -------- Run stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy all jars (may be 0, 1, or 2)
COPY --from=build /home/gradle/project/build/libs/ /app/

# Debug: show what was copied
RUN echo "---- COPIED FILES ----" && ls -al /app/

# Fail if none found
RUN test -n "$(ls -1 /app/*.jar 2>/dev/null)" || (echo "No JAR found in /app!" && exit 1)

# Move the first jar found to app.jar
RUN mv /app/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]