FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/bank-server-1.0.jar BankServer.jar

ENTRYPOINT ["java", "-jar", "ApiGatewayServer.jar"]
