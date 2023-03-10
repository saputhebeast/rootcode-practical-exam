FROM openjdk:17-alpine
EXPOSE 8090
ADD target/docker-lpl.jar docker-lpl.jar
ENTRYPOINT ["java", "-jar", "/docker-lpl.jar"]
