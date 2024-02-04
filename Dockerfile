FROM openjdk:21-rc-jdk

ENV ENVIRONMENT # prod/dev

COPY ./ ./

RUN ./mvnw clean package

EXPOSE 8080
# TODO rename the jar file from readapi-0.0.1-SNAPSHOT to *
COPY target/readapi-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["--spring.profiles.active=${ENVIRONMENT}"]
