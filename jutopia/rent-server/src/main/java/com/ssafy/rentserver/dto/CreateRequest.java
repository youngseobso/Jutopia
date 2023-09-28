package com.ssafy.rentserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequest {

    private String school;
    private int totalCount;
    private int grade;
    private int clazzNumber;

}
