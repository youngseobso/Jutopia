package com.ssafy.rentserver.model;

import com.ssafy.rentserver.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int position;
    @Column(precision = 30, scale = 4, nullable = false)
    private BigDecimal price;
    private String userId;

    @Column(nullable = false)
    private int clazzNumber;
    @Column(nullable = false)
    private int grade;
    @Column(nullable = false)
    private String school;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @PrePersist
    public void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }
    public void changeStatus(SeatStatus status){
        this.seatStatus = status;
    }
    public void changeUserId(String userId){
        this.userId = userId;
    }
}
