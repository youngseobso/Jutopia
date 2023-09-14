FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/apigateway-server-1.0.jar ApiGatewayService.jar

ENTRYPOINT ["java", "-jar", "ApiGatewayService.jar"]