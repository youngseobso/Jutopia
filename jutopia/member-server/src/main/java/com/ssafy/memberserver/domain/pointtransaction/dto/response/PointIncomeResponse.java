package com.ssafy.memberserver.domain.pointtransaction.dto.response;

import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PointIncomeResponse(
        String place,
        BigDecimal income
) {
    public static PointIncomeResponse from(PointTransaction pointTransaction){
        return PointIncomeResponse.builder()
                .income(pointTransaction.getIncome())
                .place(pointTransaction.getPlace())
                .build();

    }
}
