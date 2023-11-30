package com.ssafy.memberserver.domain.mail.service;

import com.ssafy.memberserver.common.config.RedisService;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final TeacherRepository teacherRepository;

    public static final String code = createCode();
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        if(checkDuplicatedEmail(to)) {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.addRecipients(Message.RecipientType.TO, to);// 보내는 대상
            message.setSubject("주토피아 이메일 인증");// 제목
            String msgg = "";
            msgg += "<div style='margin:100px;'>";
            msgg += "<h1> 안녕하세요</h1>";
            msgg += "<h1> 주토피아 입니다.</h1>";
            msgg += "CODE : <strong>";
            msgg += code + "</strong><div><br/> ";
            msgg += "</div>";
            message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
            // 보내는 사람의 이메일 주소, 보내는 사람 이름
            message.setFrom(new InternetAddress("hans0537@naver.com", "Jutopia"));// 보내는 사람
            return message;
        }else{
            throw new NoSuchProviderException("존재하는 이메일 입니다");
        }
    }
    // 랜덤 인증 코드 생성
    public static String createCode() {
        int codeLength = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < codeLength; i++) {
                code.append(random.nextInt(10));
            }
            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("생성 오류");
        }
    }
    public String sendSimpleMessage(String to) throws Exception {
        // 랜덤 인증번호 생성
        redisService.setValuesWithTimeout(code,to,60 * 1L);
        MimeMessage message = createMessage(to); // 메일 발송
        try {// 예외처리
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return code; // 메일로 보냈던 인증 코드를 서버로 반환
    }
    private boolean checkDuplicatedEmail(String email){
        Optional<Teacher> teacher = teacherRepository.findByTeacherEmail(email);
        if(teacher.isPresent()){
            return false;
        } else{
            return true;
        }
    }
    public String verifyEmail(String key){
        String teacherEmail = redisService.getValues(key);
        redisService.deleteValues(key);
        return code;
    }
}
