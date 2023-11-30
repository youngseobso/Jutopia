package com.ssafy.classserver.jpa.entity;

import com.ssafy.classserver.jpa.entity.ClassRoomEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "savingproducts")
public class SavingProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "SAVING_PRODUCTS_ID")
    private UUID id;

    @Column(nullable = false)
    private String productName;

    private String productDetail;

    @Column(nullable = false)
    private BigDecimal minMoney;
    @Column(nullable = false)
    private BigDecimal maxMoney;
    @Column(nullable = false)
    private Double InterestRate;
    @Column(nullable = false)
    private Short term;

    // 어느 반(은행) 에서 만든 적금상품인지
    @ManyToOne
    @JoinColumn(name = "CLASSROOM_ID")
    private ClassRoomEntity classroom;
}
