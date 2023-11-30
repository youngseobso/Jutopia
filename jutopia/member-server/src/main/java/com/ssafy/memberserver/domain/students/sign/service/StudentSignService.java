package com.ssafy.memberserver.domain.students.sign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.error.ErrorCode;
import com.ssafy.memberserver.common.exception.ApiException;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;

import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpResponse;

import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentSignService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Transactional
    public StudentSignUpResponse studentSignUp(StudentSignUpRequest studentSignUpRequest) {
        if (studentRepository.findByStudentId(studentSignUpRequest.getStudentId()).isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "아이디가 중복입니다.");
        }
        Student student = studentRepository.save(Student.from(studentSignUpRequest, passwordEncoder));
        studentRepository.flush();
        String accountNumber = CreateAccountNumber.generateAccountNumber();
        Account account = accountRepository.save(Account.from(student,accountNumber));
        accountRepository.flush();
        return StudentSignUpResponse.from(student);
    }
    public boolean checkIdDuplicated(String memberId) {
        boolean student = studentRepository.existsByStudentId(memberId);
        boolean teacher = teacherRepository.existsByTeacherId(memberId);
        if (student == false && teacher == false) {
            return false;
        }
        return true;
    }
    public class CreateAccountNumber {
        public static String generateAccountNumber() {
            // 12자리의 랜덤 숫자 생성
            String accountNumber = generateRandomNumbers(12);

            // 포맷 형식에 맞게 변경
            return formatAccountNumber(accountNumber);
        }

        private static String generateRandomNumbers(int length) {
            Random random = new Random();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                stringBuilder.append(random.nextInt(10));
            }
            return stringBuilder.toString();
        }

        private static String formatAccountNumber(String accountNumber) {
            // "xxx-xxxxxx-xxx" 형식으로 포맷팅
            return String.format("%s-%s-%s",
                    accountNumber.substring(0, 3),
                    accountNumber.substring(3, 9),
                    accountNumber.substring(9));
        }
    }
}
