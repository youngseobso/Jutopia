package com.ssafy.memberserver.domain.teachers.dto.request;

public record TeacherUpdateRequest(
        String teacherId,
        String teacherPwd,
        String teacherNewPwd,
        String teacherName,
        String teacherEmail
) {
}
