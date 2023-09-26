package com.ssafy.memberserver.domain.teachers.dto.response;

public record TeacherDeleteResponse(
        boolean TeacherDeleteResult
) {
    public static TeacherDeleteResponse of(boolean TeacherDeleteResult){
        return new TeacherDeleteResponse(TeacherDeleteResult);
    }
}
