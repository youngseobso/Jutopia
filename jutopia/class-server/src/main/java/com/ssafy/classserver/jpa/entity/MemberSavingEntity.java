package com.ssafy.classserver.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "membersaving")
public class MemberSavingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBERSAVING_ID")
    private Long id;

    // 적금 든 사람
    private UUID memberId;

    // 적금 가입 금액(일정 기간마다 나가는 돈)
    private BigDecimal money;

    // 가입 날짜
    private LocalDateTime starteddate;
    // 만기 일 (가입 날짜 + 적금상품 기간)
    private LocalDateTime expireddate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SAVING_PRODUCTS_ID")
    private SavingProductsEntity savingProduct;

    // 엔터티가 저장되기 전에 시작일과 만기일 설정
    @PrePersist
    public void prePersist() {
        this.starteddate = LocalDateTime.now();
        if (savingProduct != null && savingProduct.getTerm() != null) {
            this.expireddate = starteddate.plusMonths(savingProduct.getTerm());
        }
    }
}
