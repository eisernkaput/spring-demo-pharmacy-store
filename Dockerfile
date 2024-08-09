FROM openjdk:22
WORKDIR /app
COPY ./target/spring-demo-pharmacy-store-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","app.jar"]
