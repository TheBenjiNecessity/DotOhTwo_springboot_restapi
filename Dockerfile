FROM openjdk:21-rc-jdk

ENV ENVIRONMENT # prod/dev

COPY ./ ./

RUN ./mvnw clean package

EXPOSE 8080

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
CMD ["--spring.profiles.active=${ENVIRONMENT}"]
