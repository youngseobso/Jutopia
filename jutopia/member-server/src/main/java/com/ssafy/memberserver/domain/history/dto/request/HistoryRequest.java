package com.ssafy.memberserver.domain.history.dto.request;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.account.entity.Account;

import java.math.BigDecimal;
import java.util.UUID;

public record HistoryRequest(
        Long id,
        BigDecimal amount,
        String list,
        HistoryType historyType,
        UUID accountId
) {
}
