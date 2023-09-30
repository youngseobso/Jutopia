package com.ssafy.memberserver.domain.students.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberPointUpdateRequest(
        UUID id,
        BigDecimal point

) {
}
