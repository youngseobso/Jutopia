package com.ssafy.memberserver.domain.history.dto.response;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HistoryExpenseResponse(
        Long id,
        String sender,
        String receiver,
        BigDecimal amount,
        BigDecimal balance,
        HistoryType historyType,
        LocalDateTime createdAt,

        UUID accountId
) {
    public static HistoryExpenseResponse from(History history){
        return HistoryExpenseResponse.builder()
                .id(history.getId())
                .sender(history.getSender())
                .receiver(history.getReceiver())
                .amount(history.getAmount())
                .balance(history.getBalance())
                .historyType(history.getHistoryType())
                .accountId(history.getAccount().getId())
                .createdAt(history.getCreatedAt())
                .build();
    }
}
