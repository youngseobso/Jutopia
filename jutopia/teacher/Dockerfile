FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/teacher-1.0 teacher.jar

ENTRYPOINT ["java", "-jar", "teacher.jar"]