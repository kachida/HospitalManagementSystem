FROM amazoncorretto:16-alpine-jdk
COPY ./target/user-service-0.0.1-SNAPSHOT.jar /home/user-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
EXPOSE 5601
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/home/user-service-0.0.1-SNAPSHOT.jar"]