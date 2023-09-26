FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/rent-server-1.0.jar RentServer.jar

ENTRYPOINT ["java", "-jar", "RentServer.jar"]