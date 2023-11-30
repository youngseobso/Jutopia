package com.ssafy.memberserver.domain.pointtransaction.dto.response;

import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PointExpenseResponse(
        BigDecimal expense,
        String place,
        UUID studentId
) {
    public static PointExpenseResponse from(PointTransaction pointTransaction){
        return PointExpenseResponse.builder()
                .place(pointTransaction.getPlace())
                .expense(pointTransaction.getExpense())
                .build();
    }
}
