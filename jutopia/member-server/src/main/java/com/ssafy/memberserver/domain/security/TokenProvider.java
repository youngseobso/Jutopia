package com.ssafy.memberserver.domain.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.memberserver.common.config.YamlLoadFactory;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


@RequiredArgsConstructor
@Service
@PropertySource(value = {"token.yaml"}, factory = YamlLoadFactory.class)
public class TokenProvider {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${secret-key}")
    String secretKey;
    @Value("${expiration-minutes}")
    long expirationMinutes;

    public String createToken(String userSpecification){
        String[] userInfo = userSpecification.split(":");
        String studentId = userInfo[0];
        Optional<Student> student = studentRepository.findByStudentId(studentId);
        Student temp = student.get();

        Map<String, String> tokenSub = new HashMap<>();
        tokenSub.put("school",temp.getSchool());
        tokenSub.put("grade", String.valueOf(temp.getGrade()));
        tokenSub.put("classroom", String.valueOf(temp.getClassRoom()));
        tokenSub.put("studentNumber", String.valueOf(temp.getStudentNumber()));

        return Jwts.builder()
                        .setIssuer("jutopia")
                        .setIssuedAt(new Date())
                        .setSubject(tokenSub.toString())
                        .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.HOURS)))
                        .signWith(new SecretKeySpec(secretKey.getBytes(),SignatureAlgorithm.HS512.getJcaName()))
                .compact();
    }
    public String TeacherCreateToken(String userSpecification){
        String[] userInfo = userSpecification.split(":");
        String tearchId = userInfo[0];
        Optional<Teacher> teacher = teacherRepository.findByTeacherId(tearchId);
        Teacher temp = teacher.get();


        return Jwts.builder()
                .setIssuer("jutopia")
                .setIssuedAt(new Date())
                .setSubject(teacher.get().getTeacherEmail())
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.HOURS)))
                .signWith(new SecretKeySpec(secretKey.getBytes(),SignatureAlgorithm.HS512.getJcaName()))
                .compact();
    }

public String validateTokenAndGetSubject(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}
    public String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }
}
