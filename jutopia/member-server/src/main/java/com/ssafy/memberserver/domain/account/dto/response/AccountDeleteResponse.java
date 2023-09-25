package com.ssafy.memberserver.domain.account.dto.response;

public record AccountDeleteResponse(
        boolean result
) {
    public static AccountDeleteResponse of(boolean AccountDeleteResult){
        return new AccountDeleteResponse(AccountDeleteResult);
    }
}
