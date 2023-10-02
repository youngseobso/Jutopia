package com.ssafy.memberserver.domain.students.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberMoneyUpdateRequest(
        UUID id,
        BigDecimal money

) {
}
