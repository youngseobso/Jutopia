package com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.response;

import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import lombok.Builder;

@Builder
public record TeacherSignUpResponse(
        String teacherName,
        String teacherEmail
) {
    public static TeacherSignUpResponse from(Teacher teacher){
        return TeacherSignUpResponse.builder()
                .teacherName(teacher.getTeacherName())
                .teacherEmail(teacher.getTeacherEmail())
                .build();
    }
}