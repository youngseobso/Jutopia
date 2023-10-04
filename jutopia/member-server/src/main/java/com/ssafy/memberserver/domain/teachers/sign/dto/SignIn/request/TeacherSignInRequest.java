package com.ssafy.memberserver.domain.teachers.sign.dto.SignIn.request;

public record TeacherSignInRequest(
        String teacherId,
        String teacherPwd,
        String teacherName,
        String email
) {
}
