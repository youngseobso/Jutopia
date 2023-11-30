package com.ssafy.memberserver.domain.history.dto.response;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Builder
public record AccountHistoryResponse(
        Long id,
        String sender,
        String receiver,
        BigDecimal amount,
        BigDecimal balance,
        HistoryType historyType,
        LocalDateTime createdAt,
        UUID accountId
) {
    public static AccountHistoryResponse from(History history){
        return AccountHistoryResponse.builder()
                .id(history.getId())
                .sender(history.getSender())
                .receiver(history.getReceiver())
                .amount(history.getAmount())
                .balance(history.getBalance())
                .historyType(history.getHistoryType())
                .createdAt(history.getCreatedAt())
                .accountId(history.getAccount().getId())
                .build();
    }
}
