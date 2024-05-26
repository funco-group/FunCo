FROM docker
#COPY --from=docker/buildx-bin:latest /buildx /usr/libexec/docker/cli-plugins/docker-buildx

FROM openjdk:17-jdk

EXPOSE 8007

ARG JAR_FILE=build/libs/funco-note-0.0.1-SNAPSHOT.jar

ENV SPRING_PROFILES_ACTIVE=deploy

ADD ${JAR_FILE} funco-note.jar

ENTRYPOINT ["java", "-jar", "/funco-note.jar"]
