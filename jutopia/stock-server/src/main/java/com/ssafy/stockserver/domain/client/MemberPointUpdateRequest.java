package com.ssafy.stockserver.domain.client;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberPointUpdateRequest(
        UUID id,
        BigDecimal point

) {
}
