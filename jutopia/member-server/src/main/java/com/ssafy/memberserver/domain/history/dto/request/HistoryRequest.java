package com.ssafy.memberserver.domain.history.dto.request;

import com.ssafy.memberserver.common.enums.HistoryType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class HistoryRequest {
    private Long id;
    private BigDecimal amount;
    private String list;
    private HistoryType historyType;
    private UUID accountId;
}
