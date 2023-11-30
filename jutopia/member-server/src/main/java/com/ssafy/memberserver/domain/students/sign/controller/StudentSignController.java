package com.ssafy.memberserver.domain.students.sign.controller;

import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.domain.students.client.ClassServerClient;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpResponse;
import com.ssafy.memberserver.domain.students.sign.service.StudentSignService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/member-server/api/student")
public class StudentSignController {
    private final StudentSignService studentSignService;
    private final ClassServerClient classServerClient;
    @Operation(summary = "학생 회원가입")
    @PostMapping("/sign-up")
    public Api<StudentSignUpResponse> StudentSignUp(@RequestBody StudentSignUpRequest studentSignUpRequest){
        return Api.OK(studentSignService.studentSignUp(studentSignUpRequest));
    }
    @Operation(summary = "학생 회원가입 아이디 중복 체크")
    @GetMapping("/sign-up/{memberId}/duplicated")
    public Api<?> checkStudentIdDuplicated(@PathVariable String memberId){
        return Api.OK(studentSignService.checkIdDuplicated(memberId));
    }

    // Feign-------------------------
    // 회원가입 할때 학교 조회 하기
    @GetMapping("/{schoolName}/{grade}/{classNum}")
    public Api<UUID> getClassroomId(@PathVariable("schoolName") String schoolName,
                                    @PathVariable("grade") int grade,
                                    @PathVariable("classNum") int classNum) {

        return Api.OK(classServerClient.getClassroomId(schoolName, grade, classNum));
    }
}

