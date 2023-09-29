package com.ssafy.classserver.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDto implements Serializable {
    private UUID classroomId;

    private String bankName;

    private String productName;
    private String productDetail;

    private double interestRate;
    private BigDecimal maxMoney;
    private BigDecimal minMoney;
    private Short term;
}
