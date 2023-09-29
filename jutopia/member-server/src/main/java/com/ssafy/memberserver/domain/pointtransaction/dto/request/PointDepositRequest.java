package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record PointDepositRequest(
        BigDecimal deposit,
        String place,
        UUID studentId
) {
}
