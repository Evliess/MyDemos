FROM openjdk:11.0.14-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application-prod.yml application.yml
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]