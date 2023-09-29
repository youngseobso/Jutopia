package com.ssafy.memberserver.domain.pointtransaction.dto.request;

import com.ssafy.memberserver.common.enums.PointType;

import java.math.BigDecimal;

public record PointUpdateRequest(
        String place,
        BigDecimal deposit,
        BigDecimal output,
        PointType pointType
){
}
