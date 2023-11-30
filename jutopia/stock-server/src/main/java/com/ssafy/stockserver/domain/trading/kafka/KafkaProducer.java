//package com.ssafy.stockserver.domain.trading.kafka;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.stockserver.domain.trading.vo.request.RequestTrade;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class KafkaProducer {
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public RequestTrade send(String topic, RequestTrade trade) {
//        ObjectMapper mapper = new ObjectMapper();
//
//        String jsonInString = "";
//        try {
//            jsonInString = mapper.writeValueAsString(trade);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        kafkaTemplate.send(topic, jsonInString);
//        log.info("Kafka Producer sent data from the Stock Microservice: " + trade);
//
//        return trade;
//    }
//}
