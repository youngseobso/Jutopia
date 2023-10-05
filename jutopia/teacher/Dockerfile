FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/teacher-server-1.0.jar teacher.jar

ENTRYPOINT ["java", "-jar", "teacher.jar"]