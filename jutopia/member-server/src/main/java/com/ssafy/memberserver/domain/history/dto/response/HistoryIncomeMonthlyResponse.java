package com.ssafy.memberserver.domain.history.dto.response;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HistoryIncomeMonthlyResponse(
        UUID accountId,
        HistoryType historyType,
        String sender,
        String receiver,
        BigDecimal amount,
        BigDecimal balance,
        LocalDateTime createdAt
) {
    public HistoryIncomeMonthlyResponse from(History history){
        return HistoryIncomeMonthlyResponse.builder()
                .accountId(history.getAccount().getId())
                .historyType(HistoryType.INCOME)
                .sender(history.getSender())
                .receiver(history.getReceiver())
                .amount(history.getAmount())
                .balance(history.getBalance())
                .createdAt(history.getCreatedAt())
                .build();
    }
}

