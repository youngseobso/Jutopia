package com.ssafy.memberserver.domain.teachers.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherDeleteRequest;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherUpdateRequest;
import com.ssafy.memberserver.domain.teachers.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-server/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @Operation(summary = "선생님 회원 수정")
    @PutMapping("/update")
    public ApiResponse teacherUpdate(@RequestBody TeacherUpdateRequest teacherUpdateRequest){
        return ApiResponse.success(teacherService.teacherUpdate(teacherUpdateRequest));
    }
    @Operation(summary = "선생님 회원 삭제")
    @DeleteMapping("/delete")
    public ApiResponse teacherDelete(@RequestBody TeacherDeleteRequest teacherDeleteRequest){
        return ApiResponse.success(teacherService.teacherDelete(teacherDeleteRequest));
    }
    @Operation(summary = "기본 지원금(계좌로 송금)")
    @PutMapping("/helpMoney")
    public ApiResponse helpMony(@RequestParam String school, @RequestParam int grade, @RequestParam int classroom, @RequestParam BigDecimal income){
        return ApiResponse.success(teacherService.helpMoney(school, grade, classroom, income));
    }
}
