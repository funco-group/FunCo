FROM docker
#COPY --from=docker/buildx-bin:latest /buildx /usr/libexec/docker/cli-plugins/docker-buildx

FROM openjdk:17-jdk

EXPOSE 8761

ARG JAR_FILE=build/libs/funco_eureka-0.0.1-SNAPSHOT.jar

#ENV SPRING_PROFILES_ACTIVE=deploy

ADD ${JAR_FILE} funco-eureka.jar

ENTRYPOINT ["java", "-jar", "/funco-eureka.jar"]
