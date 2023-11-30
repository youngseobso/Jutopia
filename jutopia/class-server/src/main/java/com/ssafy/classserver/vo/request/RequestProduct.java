package com.ssafy.classserver.vo.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;

@Data
// 적금 상품 생성
public class RequestProduct {
    @NotNull(message = "적금 상품 이름을 설정하세요")
    private String productName;

    private String productDetail;

    @NotNull(message = "최소 금액을 설정하세요")
    @Min(value = 0, message = "최소 금액은 0 이상이어야 합니다.")
    private BigDecimal minMoney;

    @NotNull(message = "최대 금액을 설정하세요")
    private BigDecimal maxMoney;

    @NotNull(message = "이자율을 설정하세요")
    @DecimalMin(value = "0.0", inclusive = false, message = "이자율은 0 이상이어야 합니다.")
    private double interestRate;

    @NotNull(message = "적금 기간을 설정하세요")
    @Positive(message = "값은 양수이어야 합니다.")
    private int term;
}
