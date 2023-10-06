package com.ssafy.memberserver.domain.students.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class MemberMoneyUpdateRequest {
    private UUID id;
    private BigDecimal money;
}
