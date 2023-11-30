package com.ssafy.classserver.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "classroom")
public class ClassRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CLASSROOM_ID")
    private UUID id;

    private int classNum;               // 몇 반 인지

    private String bankName;            // 은행이름
    private String moneyCode;           // 발급하는 화폐 코드
    private String moneyName;           // 발급하는 화폐 이름
    private BigDecimal totalMoney;      // 발급한 총 화폐량
    private BigDecimal initMoney;       // 초기 발급량
    private BigDecimal classMoney;      // 반이 가지고 있는 총 화폐량
    private double exchangeTaxRate;     // 환전세
    private double consumptionTaxRate;  // 소비세
    private double tradeTaxRate;        // 거래세
    private double capitalGainsTax;     // 양도소득세

    @ManyToOne
    @JoinColumn(name = "GRADE_ID")
    private GradeEntity grade;
}
