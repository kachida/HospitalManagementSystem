FROM amazoncorretto:16-alpine-jdk
COPY ./target/config-server.jar /home/config-server.jar
EXPOSE 8888
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/home/config-server.jar"]