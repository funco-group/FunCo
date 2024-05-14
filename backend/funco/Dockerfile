FROM docker
#COPY --from=docker/buildx-bin:latest /buildx /usr/libexec/docker/cli-plugins/docker-buildx

FROM openjdk:17-jdk

EXPOSE 8080

ARG JAR_FILE=build/libs/funco-0.0.1-SNAPSHOT.jar

ENV SPRING_PROFILES_ACTIVE=deploy

ADD ${JAR_FILE} funco-springboot.jar

ENTRYPOINT ["java", "-jar", "/funco-springboot.jar"]
