package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record PointIncomeRequest(
        BigDecimal income,
        String place,
        UUID studentId
) {
}
