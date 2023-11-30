package com.ssafy.memberserver.domain.teachers.sign.controller;


import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.mail.service.MailService;

import com.ssafy.memberserver.domain.students.sign.service.StudentSignService;;
import com.ssafy.memberserver.domain.teachers.dto.request.MailConfirmRequest;
import com.ssafy.memberserver.domain.teachers.dto.request.VerifyRequest;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request.TeacherSignUpRequest;
import com.ssafy.memberserver.domain.teachers.sign.service.TeacherSignService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member-server/api/teacher")
public class TeacherSignController {
    private final TeacherSignService teacherSignService;
    private final StudentSignService studentSignService;
    private final MailService mailService;

    @Operation(summary = "선생님 회원 가입")
    @PostMapping("/sign-up")
    public ApiResponse teacherSignUp(@RequestBody TeacherSignUpRequest teacherSignUpRequest){
        return ApiResponse.success(teacherSignService.teacherSignUp(teacherSignUpRequest));
    }
    @Operation(summary = "선생님 아이디 중복 검사")
    @GetMapping("/sign-up/{memberId}/duplicated")
    public ApiResponse checkTeacherIdDuplicated(@PathVariable String memberId){
        return ApiResponse.success(teacherSignService.checkIdDuplicated(memberId));
    }

    @Operation(summary = "이메일 인증 코드 발송")
    @PostMapping("/sign-in/mailConfirm")
    @ResponseBody
    public ApiResponse mailConfirm(@RequestBody MailConfirmRequest mailConfirmRequest) throws Exception{
        return ApiResponse.success(mailService.sendSimpleMessage(mailConfirmRequest.getEmailId()));
    }
    @Operation(summary = "이메일 인증 코드 비교")
    @PostMapping("/sign-in/verifyCode")
    @ResponseBody
    public boolean verifyCode(@RequestBody VerifyRequest verifyRequest) throws ChangeSetPersister.NotFoundException {
        boolean result = false;
        String temp = mailService.verifyEmail(verifyRequest.getCode());
        if(temp.equals(verifyRequest.getCode())){
            return result;
        }
        return true;
    }
}

