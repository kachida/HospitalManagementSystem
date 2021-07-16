FROM amazoncorretto:16-alpine-jdk
COPY ./target/patient-service-0.0.1-SNAPSHOT.jar /home/patient-service-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/home/patient-service-0.0.1-SNAPSHOT.jar"]