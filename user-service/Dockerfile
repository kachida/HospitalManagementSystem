FROM amazoncorretto:16-alpine-jdk
COPY ./target/user-service.jar /home/user-service.jar
EXPOSE 8081
EXPOSE 5601
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/home/user-service.jar"]