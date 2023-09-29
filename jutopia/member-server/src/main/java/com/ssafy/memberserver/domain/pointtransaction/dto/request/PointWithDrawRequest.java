package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record PointWithDrawRequest(
        BigDecimal withDraw,
        String place,
        UUID studentId
) {
}
