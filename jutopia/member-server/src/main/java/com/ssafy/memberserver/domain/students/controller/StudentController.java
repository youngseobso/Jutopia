package com.ssafy.memberserver.domain.students.controller;

import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.students.dto.request.*;
import com.ssafy.memberserver.domain.students.dto.response.StudentDeleteResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentInfoResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentPointUpdateResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentUpdateResponse;
import com.ssafy.memberserver.domain.students.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member-server/api/student")
public class StudentController {
    private final StudentService studentService;

    @Operation(summary = "학생 조회")
    @GetMapping
    public Api<StudentInfoResponse> getStudent(@RequestParam String studentId){
        return Api.OK(studentService.getStudentInfo(studentId));
    }

    @Operation(summary = "학생 정보 수정")
    @PutMapping("/update")
    public Api<StudentUpdateResponse> studentUpdate(@RequestBody StudentUpdateRequest studentUpdateRequest){
        return Api.OK(studentService.studentUpdate(studentUpdateRequest));
    }

    @Operation(summary = "학생 탈퇴")
    @DeleteMapping("/delete")
    public Api<StudentDeleteResponse> studentDelete(@RequestBody StudentDeleteRequest studentDeleteRequest){
        return Api.OK(studentService.studentDelete(studentDeleteRequest));
    }
    @Operation(summary = "임대 학생 포인트 차감")
    @PutMapping("/point")
    public Api<?> studentPointUpdate(@RequestBody StudentPointUpdateRequest studentPointUpdateRequest){
        return studentService.studentPointUpdate(studentPointUpdateRequest);
    }

    // Feign ---------------------------------------------------
    @GetMapping("/feign/{userId}")
    public ApiResponse getMember(@PathVariable UUID userId){
        System.out.println("member 호출1: " + userId);
        System.out.println("member 호출2: " + userId.getClass());
        return ApiResponse.success(studentService.getMemberInfo(userId));
    }

    @PutMapping("/feign/point")
    public ApiResponse memberPointUpdate(@RequestBody MemberPointUpdateRequest memberPointUpdateRequest){
        return ApiResponse.success(studentService.memberPointUpdate(memberPointUpdateRequest));
    }
    @PutMapping("/feign/money")
    public ApiResponse memberMoneyUpdate(@RequestBody MemberMoneyUpdateRequest memberMoneyUpdateRequest){
        return ApiResponse.success(studentService.memberMoneyUpdate(memberMoneyUpdateRequest));
    }
}
