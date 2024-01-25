
FROM openjdk:21-rc-jdk

COPY ./ ./

RUN ./mvnw clean package

EXPOSE 8080
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"] #, "--spring.profiles.active=${ENVIRONMENT}"] # --spring.profiles.active=prod/dev
