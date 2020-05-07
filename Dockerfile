FROM gradle:6.3.0-jdk11 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM gcr.io/distroless/java:11

COPY --from=build /home/gradle/src/build/libs/*.jar /app/guice-test.jar

WORKDIR /app

CMD ["guice-test.jar"]