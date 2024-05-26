FROM docker
FROM openjdk:17-jdk

EXPOSE 8009

ARG JAR_FILE=build/libs/funco-notification-0.0.1-SNAPSHOT.jar

ENV SPRING_PROFILES_ACTIVE=deploy

ADD ${JAR_FILE} funco-notification.jar

ENTRYPOINT ["java", "-jar", "/funco-notification.jar"]
