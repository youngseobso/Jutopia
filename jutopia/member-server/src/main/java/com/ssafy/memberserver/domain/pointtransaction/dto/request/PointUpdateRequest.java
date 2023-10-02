package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import com.ssafy.memberserver.common.enums.HistoryType;

import java.math.BigDecimal;

public record PointUpdateRequest(
        String place,
        BigDecimal income,
        BigDecimal expense,
        HistoryType historyType
){
}
