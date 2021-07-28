FROM amazoncorretto:16-alpine-jdk
COPY ./target/patient-service.jar /home/patient-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/home/patient-service.jar"]