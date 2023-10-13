#Describe step by step how the code below works
#1. Use ubuntu as base image
#2. Install openjdk-17-jdk
#3. Copy all files from current directory to the container
#4. Install maven
#5. Run maven clean install
#6. Expose port 8080
#7. Copy the jar file from target to app.jar
#8. Run the jar file

FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080

# Copy the jar file from target to app.jar
COPY --from=build /target/todo-list-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]