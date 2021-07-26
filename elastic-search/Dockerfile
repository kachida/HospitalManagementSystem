FROM amazoncorretto:16-alpine-jdk
COPY ./target/elastic-search-2.5.0.jar /home/elastic-search-2.5.0.jar
EXPOSE 8084
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/home/elastic-search-2.5.0.jar"]