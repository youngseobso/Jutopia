FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/apigateway-server-0.0.1-SNAPSHOT.jar ApiGatewayService.jar

ENTRYPOINT ["java", "-jar", "ApiGatewayService.jar"]
