package com.ssafy.classserver.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDto implements Serializable {
    private UUID bankId;
    private double interestRate;
    private BigDecimal maxMoney;
    private BigDecimal minMoney;
    private Short term;
}
