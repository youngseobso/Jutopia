package com.ssafy.classserver.client;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberMoneyUpdateRequest(
        UUID id,
        BigDecimal money

) {
}
