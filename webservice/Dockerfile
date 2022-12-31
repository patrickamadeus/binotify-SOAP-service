FROM maven:3.6.1-jdk-11
WORKDIR /app
EXPOSE 8080
COPY . .
RUN mvn clean compile assembly:single
COPY . .
CMD ["java","-jar","./target/webservice-1.0-jar-with-dependencies.jar"]