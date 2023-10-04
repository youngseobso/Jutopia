package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ReceiveMoneyRequest {
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
    private String studentId;
}
