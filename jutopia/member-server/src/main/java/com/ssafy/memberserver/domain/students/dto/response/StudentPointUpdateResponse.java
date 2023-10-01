package com.ssafy.memberserver.domain.students.dto.response;

public record StudentPointUpdateResponse(
        boolean StudentPointUpdateResult
) {
    public static StudentPointUpdateResponse of(boolean StudentPointUpdateResult){
        return new StudentPointUpdateResponse(StudentPointUpdateResult);
    }
}
