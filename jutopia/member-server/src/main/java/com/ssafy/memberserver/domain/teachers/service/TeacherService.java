package com.ssafy.memberserver.domain.teachers.service;

import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherDeleteRequest;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherUpdateRequest;
import com.ssafy.memberserver.domain.teachers.dto.response.HelpMoney;
import com.ssafy.memberserver.domain.teachers.dto.response.TeacherDeleteResponse;
import com.ssafy.memberserver.domain.teachers.dto.response.TeacherUpdateResponse;
import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.response.TeacherSignUpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TeacherUpdateResponse teacherUpdate(TeacherUpdateRequest teacherUpdateRequest){
        return teacherRepository.findByTeacherId(teacherUpdateRequest.getTeacherId())
                .filter(teacher -> passwordEncoder.matches(teacherUpdateRequest.getTeacherPwd(),teacher.getTeacherPwd()))
                .map(teacher -> {
                    teacher.update(teacherUpdateRequest,passwordEncoder);
                    return TeacherUpdateResponse.of(true);
                })
                .orElseThrow(() -> new NoSuchElementException("비밀번호가 다릅니다."));
    }
    @Transactional
    public TeacherDeleteResponse teacherDelete(TeacherDeleteRequest teacherDeleteRequest) {
        return teacherRepository.findByTeacherId(teacherDeleteRequest.getTeacherId())
                .filter(teacher -> passwordEncoder.matches(teacherDeleteRequest.getTeacherPwd(),teacher.getTeacherPwd()))
                .map(teacher -> {
                    teacher.delete(teacherDeleteRequest);
                    return TeacherDeleteResponse.of(true);
                })
                .orElseThrow(() -> new NoSuchElementException("비밀번호가 일치하지 않습니다"));
    }
    @Transactional
    public List<HelpMoney> helpMoney(String school, int grade, int classroom, BigDecimal income) {
        List<Student> students = studentRepository.findBySchoolAndGradeAndAndClassRoom(school, grade, classroom);
        return students.stream()
                .map(student -> {
                    Account account = accountRepository.findAccountByStudentId(student.getStudentId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + student.getStudentId()));
                    account.helpMoneyUpdate(income);
                    HelpMoney helpMoneyResponse = new HelpMoney();
                    return helpMoneyResponse;
                })
                .collect(Collectors.toList());
    }
}
