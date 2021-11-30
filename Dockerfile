FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} awezomestore_session_ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "/awezomestore_session_ms-0.0.1-SNAPSHOT.jar" ]