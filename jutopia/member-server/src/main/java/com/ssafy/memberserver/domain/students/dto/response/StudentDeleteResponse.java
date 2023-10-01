package com.ssafy.memberserver.domain.students.dto.response;

public record StudentDeleteResponse(
        boolean studentDeleteResult
) {
    public static StudentDeleteResponse of(boolean studentDeleteResult){
        return new StudentDeleteResponse(studentDeleteResult);
    }
}
