FROM amazoncorretto:17 as gateway
ARG JAR_FILE=target/gateway-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]

EXPOSE 80