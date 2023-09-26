FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/class-server-1.0.jar ClassServer.jar

ENTRYPOINT ["java", "-jar", "ClassServer.jar"]