package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import com.ssafy.memberserver.common.enums.HistoryType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PointUpdateRequest {
    private String place;
    private BigDecimal income;
    private BigDecimal expense;
    private HistoryType historyType;
}