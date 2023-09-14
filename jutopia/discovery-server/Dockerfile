FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/discovery-server-1.0.jar DiscoveryService.jar

ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]

