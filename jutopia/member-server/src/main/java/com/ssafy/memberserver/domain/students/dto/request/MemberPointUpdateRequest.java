package com.ssafy.memberserver.domain.students.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class MemberPointUpdateRequest {
    private UUID id;
    private BigDecimal point;
}

