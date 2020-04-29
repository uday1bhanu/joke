FROM openjdk:11-jdk
LABEL maintainer="Uday"

COPY /build/libs/joke-0.0.1-SNAPSHOT.jar /

EXPOSE 8083

ENV JAVA_OPTIONS ""
CMD ["sh", "-c", "java ${JAVA_OPTIONS} -jar /joke-0.0.1-SNAPSHOT.jar"]