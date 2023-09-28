package com.ssafy.memberserver.domain.students.dto.request;

import java.math.BigDecimal;

public record StudentPointUpdateRequest(
        String studentId,
        BigDecimal point

) {
}
