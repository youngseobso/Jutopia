package com.ssafy.classserver.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestClassRoom {

    @NotNull(message = "몇반인지 설정 해주세요")
    private int classNum;
    @NotNull(message = "은행 명을 설정해주세요")
    private String bankName;
    @NotNull(message = "발급할 화폐 코드 설정해주세요")
    private String moneyCode;
    @NotNull(message = "발급할 화폐 이름 설정해주세요")
    private String moneyName;

    private BigDecimal totalMoney;      // 발급한 총 화폐량
    private BigDecimal initMoney;       // 초기 발급량
    private BigDecimal classMoney;      // 반이 가지고 있는 총 화폐량

    private double exchangeTaxRate;     // 환전세
    private double consumptionTaxRate;  // 소비세
    private double tradeTaxRate;        // 거래세
    private double capitalGainsTax;     // 양도소득세

}
