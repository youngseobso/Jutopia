package com.ssafy.stockserver.domain.stock.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStock {
    private UUID id;

    private String stockCode;
    private String stockName;
}
