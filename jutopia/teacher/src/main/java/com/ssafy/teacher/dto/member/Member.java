package com.ssafy.teacher.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Member {
    private UUID id;
    private String memberId;
    private String name;
    private String memberBioStatus;
    private BigDecimal money;
    private BigDecimal point;
    private String memberRole;
    private String memberStatus;
    private String school;
    private int grade;
    private int classroom;
    private String studentNumber;
    private String seatOwnershipStatus;
    private String token;
}
