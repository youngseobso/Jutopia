package com.ssafy.memberserver.domain.students.dto.response;

public record MemberMoneyUpdateResponse(
        boolean MemberMoneyUpdateResult
) {
    public static MemberMoneyUpdateResponse of(boolean MemberMoneyUpdateResult){
        return new MemberMoneyUpdateResponse(MemberMoneyUpdateResult);
    }
}
