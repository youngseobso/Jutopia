package com.ssafy.memberserver.domain.students.service;

import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.common.error.ErrorCode;
import com.ssafy.memberserver.common.exception.ApiException;
import com.ssafy.memberserver.domain.students.dto.request.*;
import com.ssafy.memberserver.domain.students.dto.response.*;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;


@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public StudentInfoResponse getStudentInfo(String studentId){
            return studentRepository.findByStudentId(studentId)
                    .map(StudentInfoResponse::from)
                    .orElseThrow(() -> new ApiException(ErrorCode.STUDENT_NOT_FOUND,"존재하지 않는 학생입니다."));
        }
    @Transactional
    public StudentUpdateResponse studentUpdate(StudentUpdateRequest studentUpdateRequest){
        return studentRepository.findByStudentId(studentUpdateRequest.getStudentId())
                .filter(student -> passwordEncoder.matches(studentUpdateRequest.getStudentPwd(),student.getStudentPwd()))
                .map(student -> {
                    student.update(studentUpdateRequest,passwordEncoder);
                    return StudentUpdateResponse.of(true);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.STUDENT_NOT_FOUND,"존재하지 않는 학생입니다."));
    }
    @Transactional
    public StudentDeleteResponse studentDelete(StudentDeleteRequest studentDeleteRequest){
        return studentRepository.findByStudentId(studentDeleteRequest.getStudentId())
                .filter(student -> passwordEncoder.matches(studentDeleteRequest.getStudentPwd(),student.getStudentPwd()))
                .map(student -> {
                    student.delete(studentDeleteRequest);
                    return StudentDeleteResponse.of(true);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.STUDENT_INVALID_INPUT,"아이디 또는 비밀번호가 틀렸습니다."));
    }
    @Transactional
    public Api<?> studentPointUpdate(StudentPointUpdateRequest studentPointUpdateRequest){
        var student = studentRepository.findByStudentId(studentPointUpdateRequest.getStudentId());
        if (student.isEmpty()) {
            return Api.ERROR(ErrorCode.STUDENT_NOT_FOUND, "존재하지 않는 학생입니다.");
        }
        try {
            var studentInfo = student.get();
            studentInfo.pointUpdate(studentPointUpdateRequest);
            studentInfo.changeSeat(studentPointUpdateRequest.getSeatId());
            return Api.OK(StudentPointUpdateResponse.of(true));
        } catch (ApiException e){
            return Api.ERROR(ErrorCode.STUDENT_POINT_ERROR, "포인트가 부족합니다.");
        }

    }

    // Feign -------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public StudentInfoResponse getMemberInfo(UUID userId){
        return studentRepository.findById(userId)
                .map(StudentInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public MemberPointUpdateResponse memberPointUpdate(MemberPointUpdateRequest memberPointUpdateRequest) {
        return studentRepository.findById(memberPointUpdateRequest.getId())
                .map(it ->{
                    log.info("{}","ewffwefwewefefewf");
                    it.memberPointUpdate(memberPointUpdateRequest);
                    return MemberPointUpdateResponse.of(true);
                })
                .orElseThrow(()->new NoSuchElementException("kk"));
    }

    @Transactional
    public MemberMoneyUpdateResponse memberMoneyUpdate(MemberMoneyUpdateRequest memberMoneyUpdateRequest) {
        return studentRepository.findById(memberMoneyUpdateRequest.getId())
                .map(it ->{
                    log.info("{}","ewffwefwewefefewf");
                    it.memberMoneyUpdate(memberMoneyUpdateRequest);
                    return MemberMoneyUpdateResponse.of(true);
                })
                .orElseThrow(()->new NoSuchElementException("kk"));
    }
}
