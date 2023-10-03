package com.ssafy.memberserver.domain.students.dto.response;

public record StudentUpdateResponse(
        boolean studentUpdateResult
) {
    public static StudentUpdateResponse of(boolean studentUpdateResult){
        return new StudentUpdateResponse(studentUpdateResult);
    }
}
