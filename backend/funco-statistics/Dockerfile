FROM docker
#COPY --from=docker/buildx-bin:latest /buildx /usr/libexec/docker/cli-plugins/docker-buildx

FROM openjdk:17-jdk

EXPOSE 8005

ARG JAR_FILE=build/libs/funco-statistics-0.0.1-SNAPSHOT.jar

ENV SPRING_PROFILES_ACTIVE=deploy

ADD ${JAR_FILE} funco-statistics.jar

ENTRYPOINT ["java", "-jar", "/funco-statistics.jar"]
