package com.ssafy.stockserver.domain.memberStock.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestMemberStock {
    @NotNull(message = "주식 코드를 설정하세요")
    private String stockCode;
    @NotNull(message = "주식 이름을 설정하세요")
    private String stockName;
}
