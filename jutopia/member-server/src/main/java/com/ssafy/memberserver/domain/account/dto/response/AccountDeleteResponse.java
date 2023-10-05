package com.ssafy.memberserver.domain.account.dto.response;

public record AccountDeleteResponse(
        boolean AccountDeleteResult
) {
    public static AccountDeleteResponse of(boolean AccountDeleteResult){
        return new AccountDeleteResponse(AccountDeleteResult);
    }
}
