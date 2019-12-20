FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/Sesh*.jar
COPY ${JAR_FILE} Sesh-status-api.jar
ENTRYPOINT ["java","-jar","/Sesh-status-api.jar"]