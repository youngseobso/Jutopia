package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.MoneyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReceiveMoneyRequest(
        String sender,
        String receiver,
        BigDecimal amount,
        MoneyType moneyType,
        AccountStatus accountStatus,
        LocalDateTime createdAt,
        String studentId
) {
}
