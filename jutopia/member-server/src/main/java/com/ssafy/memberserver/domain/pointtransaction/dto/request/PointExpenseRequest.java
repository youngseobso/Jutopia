package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record PointExpenseRequest(
        BigDecimal expense,
        String place,
        UUID studentId
) {
}
