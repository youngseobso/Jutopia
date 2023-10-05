package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
public class PointIncomeRequest {
    private String studentId;
    private BigDecimal income;
    private String place;
}
