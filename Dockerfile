FROM openjdk:21-rc-jdk

ENV ENVIRONMENT # prod/dev

COPY ./ ./

RUN ./mvnw clean package

EXPOSE 8080
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"] # --spring.profiles.active=prod/dev
CMD ["--spring.profiles.active=${ENVIRONMENT}"]
