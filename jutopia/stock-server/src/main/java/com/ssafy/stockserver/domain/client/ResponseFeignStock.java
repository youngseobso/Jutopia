package com.ssafy.stockserver.domain.client;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseFeignStock {
    private String stockName;

    private String stockCode;
    private int price;
    private double changeRate;
    private int changeMoney;
    private int type;
}
