FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/apigateway-server-1.0.jar ApiGatewayServer.jar

ENTRYPOINT ["java", "-jar", "ApiGatewayServer.jar"]
