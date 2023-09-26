package com.ssafy.memberserver.domain.students.dto.response;

public record StudentDeleteResponse(
        boolean StudentDeleteResult
) {
    public static StudentDeleteResponse of(boolean StudentDeleteResult){
        return new StudentDeleteResponse(StudentDeleteResult);
    }
}
