FROM amazoncorretto:16-alpine-jdk
copy ./target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar
RUN apk update
RUN apk --no-cache add bash ca-certificates wget openssl
CMD ["java","-jar","user-service-0.0.1-SNAPSHOT.jar"]