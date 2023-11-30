package com.ssafy.memberserver.domain.teachers.sign.service;

import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request.TeacherSignUpRequest;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.response.TeacherSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeacherSignService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public TeacherSignUpResponse teacherSignUp(TeacherSignUpRequest teacherSignUpRequest){
        if (teacherRepository.findByTeacherId(teacherSignUpRequest.getTeacherId()).isPresent()){
            throw new IllegalStateException("아이디 중복입니다.");
        }
        Teacher teacher = teacherRepository.save(Teacher.from(teacherSignUpRequest, passwordEncoder));
        teacherRepository.flush();
        return TeacherSignUpResponse.from(teacher);
    }
    public boolean checkIdDuplicated(String memberId) {
        boolean student = studentRepository.existsByStudentId(memberId);
        boolean teacher = teacherRepository.existsByTeacherId(memberId);
        if (student == false && teacher == false) {
            return false;
        }
        return true;
    }
}
