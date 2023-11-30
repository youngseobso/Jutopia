//package com.ssafy.memberserver.domain.students.kafka;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.memberserver.domain.students.entity.Student;
//import com.ssafy.memberserver.domain.students.repository.StudentRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@Slf4j
//public class KafkaConsumer {
//    // jpa에서 값을 가져오기 때문에
//    StudentRepository studentRepository;
//
//    @Autowired
//    public KafkaConsumer(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//
//    @KafkaListener(topics = "member-point")
//    public void updatePoint(String kafkaMessage) {
//        log.info("Kafka Message: -> " + kafkaMessage);
//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            // 카프카로부터 받아온 스트링 값을 JSON으로 변환
//            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        Optional<Student> entity = studentRepository.findById((UUID)map.get("memberId"));
//        if(entity.get() != null) {
//            entity.get().setPoint(
//                    entity.get().getPoint().add(((BigDecimal)map.get("price")).multiply(BigDecimal.valueOf((Long)map.get("volume"))))
//                    );
//            studentRepository.save(entity.get());
//        }
//
//    }
//}
