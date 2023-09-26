package com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.response;

import com.ssafy.memberserver.domain.teachers.entity.Teacher;

public record TeacherSignUpResponse(
        String teacherName,
        String teacherEmail
) {
    public static TeacherSignUpResponse from(Teacher teacher){
        return new TeacherSignUpResponse(
                teacher.getTeacherName(),
                teacher.getTeacherEmail()
        );
    }
}
