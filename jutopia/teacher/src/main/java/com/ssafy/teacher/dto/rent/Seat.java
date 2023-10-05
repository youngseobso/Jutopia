package com.ssafy.teacher.dto.rent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Seat {
    private String id;
    private int position;
    private int price;
    private String user_id;
    private String seat_status;
    private int clazz_number;
    private int grade;
    private String school;

}

