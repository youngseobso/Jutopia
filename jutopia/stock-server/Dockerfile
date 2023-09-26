FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/stock-server-1.0.jar StockServer.jar

ENTRYPOINT ["java", "-jar", "StockServer.jar"]