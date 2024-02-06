FROM eclipse-temurin:21-jdk-alpine

VOLUME /tmp

ENV ENVIRONMENT # prod/dev

EXPOSE 8080

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["--spring.profiles.active=${ENVIRONMENT}"]