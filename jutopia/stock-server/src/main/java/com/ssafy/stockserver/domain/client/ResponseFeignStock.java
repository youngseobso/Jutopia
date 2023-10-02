package com.ssafy.stockserver.domain.client;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseFeignStock {
    private String stockName;

    private String nowMoney;
    private String prevMoney;
//    private String company_name;
//
//    private String latest_price;
//    private String previous_price;
}
