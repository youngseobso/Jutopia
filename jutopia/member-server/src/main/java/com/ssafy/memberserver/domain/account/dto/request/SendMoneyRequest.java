package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.HistoryType;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class SendMoneyRequest {
    private UUID id;
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private HistoryType historyType;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
    private String studentId;
}
