package com.ssafy.classserver.dto;

import com.ssafy.classserver.vo.response.ResponseClassRoom;
import com.ssafy.classserver.vo.response.ResponseGrade;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SchoolDto {
    private UUID schoolId;
    private String schoolName;
    private String schoolCode;
    private String region;

    private List<ResponseGrade> grades;
    private List<ResponseClassRoom> classRooms;
}
