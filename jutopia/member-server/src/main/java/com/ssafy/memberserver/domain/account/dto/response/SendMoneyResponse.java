package com.ssafy.memberserver.domain.account.dto.response;

public record SendMoneyResponse(
        String result_text
) {
    public SendMoneyResponse sendMoneyResponse(String result_text){
        return new SendMoneyResponse(result_text);
    }
}
