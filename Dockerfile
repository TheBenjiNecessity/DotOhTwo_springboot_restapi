FROM amazoncorretto:21-alpine

VOLUME /tmp

ENV ENVIRONMENT # prod/dev

EXPOSE 8080

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["--spring.profiles.active=${ENVIRONMENT}"]