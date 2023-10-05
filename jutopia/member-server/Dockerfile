FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/member-server-1.0.jar MemberServer.jar

ENTRYPOINT ["java", "-jar", "MemberServer.jar"]