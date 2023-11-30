package com.ssafy.rentserver.dto;

import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponse {

    private UUID id;

    private int position;

    private BigDecimal price;

    private String userId;

    private SeatStatus seatStatus;

    private int clazzNumber;

    private int grade;

    private String school;

    public static SeatResponse toResponse(Seat seat){
        return SeatResponse.builder()
                .id(seat.getId())
                .school(seat.getSchool())
                .grade(seat.getGrade())
                .clazzNumber(seat.getClazzNumber())
                .position(seat.getPosition())
                .price(seat.getPrice())
                .userId(seat.getUserId())
                .seatStatus(seat.getSeatStatus())
                .build();
    }

}
