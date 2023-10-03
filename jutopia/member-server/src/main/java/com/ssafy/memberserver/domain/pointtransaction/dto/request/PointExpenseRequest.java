package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
public class PointExpenseRequest {
    private BigDecimal expense;
    private String place;
    private UUID studentId;
}
