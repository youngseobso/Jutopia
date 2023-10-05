FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/teacher-0.0.1-SNAPSHOT-plain.jar teacher.jar

ENTRYPOINT ["java", "-jar", "teacher.jar"]