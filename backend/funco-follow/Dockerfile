FROM docker

FROM openjdk:17-jdk

EXPOSE 8007

ARG JAR_FILE=build/libs/funco-follow-0.0.1-SNAPSHOT.jar

ENV SPRING_PROFILES_ACTIVE=deploy

ADD ${JAR_FILE} funco-follow.jar

ENTRYPOINT ["java", "-jar", "/funco-follow.jar"]
