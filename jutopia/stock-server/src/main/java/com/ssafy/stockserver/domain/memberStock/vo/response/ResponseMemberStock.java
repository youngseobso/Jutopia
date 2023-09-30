package com.ssafy.stockserver.domain.memberStock.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMemberStock {

    private Stock stock;

    // 매입가
    private BigDecimal price;
    // 매입 수량
    private Long qty;
    // 거래일
    private LocalTime tradeAt;

}
