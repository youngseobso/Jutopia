package com.ssafy.memberserver.domain.teachers.dto.response;

import com.ssafy.memberserver.domain.teachers.entity.Teacher;

public record TeacherUpdateResponse(
        boolean teacherUpdateResult
) {
    public static TeacherUpdateResponse of(boolean teacherUpdateResult){
        return new TeacherUpdateResponse(teacherUpdateResult);
    }
}
