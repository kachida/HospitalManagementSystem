FROM amazoncorretto:16-alpine-jdk
COPY ./target/vital-sign-service-0.0.1-SNAPSHOT.jar /home/vital-sign-service-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/home/vital-sign-service-0.0.1-SNAPSHOT.jar"]