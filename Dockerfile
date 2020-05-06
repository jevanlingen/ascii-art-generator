FROM gradle:6.3.0-jdk11 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# FIXME: 11-jre-slim does not contain `fontconfig`, so use the non-slim variant
FROM openjdk:11

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/guice-test.jar

ENTRYPOINT ["java", "-jar", "/app/guice-test.jar"]