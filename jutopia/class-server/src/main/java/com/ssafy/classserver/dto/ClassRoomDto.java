package com.ssafy.classserver.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ClassRoomDto {
    private UUID id;
    private String schoolName;
    private int gradeNum;

    private int classNum;
    private String bankName;
    private String moneyCode;
    private String moneyName;

    private BigDecimal totalMoney;      // 발급한 총 화폐량
    private BigDecimal initMoney;       // 초기 발급량
    private BigDecimal classMoney;      // 반이 가지고 있는 총 화폐량

    private double exchangeTaxRate;     // 환전세
    private double consumptionTaxRate;  // 소비세
    private double tradeTaxRate;        // 거래세
    private double capitalGainsTax;     // 양도소득세


}
