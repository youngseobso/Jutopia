package com.ssafy.classserver.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProduct {
    private UUID id;

    private String bankName;

    private String productName;
    private String productDetail;

    private double interestRate;
    private BigDecimal maxMoney;
    private BigDecimal minMoney;
    private Short term;
}