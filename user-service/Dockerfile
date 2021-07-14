FROM amazoncorretto:16-alpine-jdk
COPY ./target/user-service-0.0.1-SNAPSHOT.jar /home/user-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/home/user-service-0.0.1-SNAPSHOT.jar"]