FROM openjdk:8-jre

EXPOSE 8080

RUN mkdir -p /var/opt/api/logs

COPY ./target/api-0.0.1-SNAPSHOT.jar /var/opt/api
WORKDIR /var/opt/api

ENTRYPOINT java -jar api-0.0.1-SNAPSHOT.jar
