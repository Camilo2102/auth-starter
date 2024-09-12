#
# Build stage
#
FROM maven:3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /target/auth-starter.jar auth-starter.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","auth-starter.jar"]