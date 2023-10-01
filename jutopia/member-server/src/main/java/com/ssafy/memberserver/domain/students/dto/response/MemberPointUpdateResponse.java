package com.ssafy.memberserver.domain.students.dto.response;

public record MemberPointUpdateResponse(
        boolean MemberPointUpdateResult
) {
    public static MemberPointUpdateResponse of(boolean MemberPointUpdateResult){
        return new MemberPointUpdateResponse(MemberPointUpdateResult);
    }
}
