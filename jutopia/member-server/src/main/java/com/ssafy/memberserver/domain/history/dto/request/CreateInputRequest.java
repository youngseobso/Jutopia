package com.ssafy.memberserver.domain.history.dto.request;

import com.ssafy.memberserver.common.enums.HistoryType;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateInputRequest(
        Long id,
        BigDecimal amount,
        String list,
        HistoryType historyType,
        UUID accountId
) {
}
