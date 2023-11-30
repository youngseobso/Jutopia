package com.ssafy.classserver.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMemberSaving {
    private UUID id;

    private UUID productId;
    private String productName;
    private String productDetail;

    // 적금 가입 금액(일정 기간마다 나가는 돈)
    private BigDecimal money;
    // 기간
    private Short term;
    // 가입 날짜
    private LocalDateTime starteddate;
    // 만기 일 (가입 날짜 + 적금상품 기간)
    private LocalDateTime expireddate;

    private double interestRate;

}
