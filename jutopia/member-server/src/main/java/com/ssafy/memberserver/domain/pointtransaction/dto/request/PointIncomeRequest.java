package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
public class PointIncomeRequest {
    private BigDecimal income;
    private String place;
    private UUID studentId;
}
