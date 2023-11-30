package com.ssafy.stockserver.domain.stock.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStock {
    private UUID id;

    private String stockCode;
    private String stockName;

    private BigDecimal nowMoney;
    private BigDecimal changeMoney;

    private Double changeRate;

    private Boolean isOwnedByUser;

    // + / -
    private int type;
}
