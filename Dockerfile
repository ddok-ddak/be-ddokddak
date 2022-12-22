FROM openjdk:17-jdk

ARG VERSION=0.0.1
ARG JAR_FILE=./build/libs/be-ddokddak-${VERSION}-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ARG PROFILE=local
ENV SPRING_PROFILES_ACTIVE=${PROFILE}

ENTRYPOINT ["java","-jar", "-Dspring.config.location=classpath:/application.yml" ,"app.jar"]