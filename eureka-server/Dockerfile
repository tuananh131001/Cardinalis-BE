FROM amazoncorretto:17 as eureka-server

ARG JAR_FILE=target/eureka-server-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]

EXPOSE 9000