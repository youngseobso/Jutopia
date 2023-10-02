package com.ssafy.memberserver.domain.account.dto.response;

public record ReceiveMoneyResponse(
        String result_code
) {
    public ReceiveMoneyResponse receiveMoney(String result_code){
        return new ReceiveMoneyResponse(result_code);
    }
}
