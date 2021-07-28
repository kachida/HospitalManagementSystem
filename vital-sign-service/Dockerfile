FROM amazoncorretto:16-alpine-jdk
COPY ./target/vital-sign-service.jar /home/vital-sign-service.jar
EXPOSE 8088
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/home/vital-sign-service.jar"]