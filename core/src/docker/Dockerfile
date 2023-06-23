FROM openjdk:17-jdk-apline
MAINTAINER baaronlubega1@gmail.com
VOLUME /tmp
#COPY build/libs*.jar /app.jar

EXPOSE 2023

ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]