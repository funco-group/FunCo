FROM docker
#COPY --from=docker/buildx-bin:latest /buildx /usr/libexec/docker/cli-plugins/docker-buildx

FROM openjdk:17-jdk

EXPOSE 8001

ARG JAR_FILE=build/libs/funco-auth-0.0.1-SNAPSHOT.jar

ENV SPRING_PROFILES_ACTIVE=deploy

ADD ${JAR_FILE} funco-auth.jar

ENTRYPOINT ["java", "-jar", "/funco-auth.jar"]
