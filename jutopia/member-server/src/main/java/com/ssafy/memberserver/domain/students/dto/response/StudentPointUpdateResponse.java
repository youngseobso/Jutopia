package com.ssafy.memberserver.domain.students.dto.response;

public record StudentPointUpdateResponse(
        String result_code
) {
    public static StudentPointUpdateResponse of(String result_code){
        return new StudentPointUpdateResponse(result_code);
    }
}
