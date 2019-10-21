FROM maven:3-jdk-11

ADD . /app
WORKDIR /app

RUN mvn clean install

FROM openjdk:11-jdk

MAINTAINER emmanuel.florent@gmail.com

VOLUME /tmp

COPY --from=0 /app/backend/target/backend-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
