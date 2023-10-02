package com.ssafy.memberserver.domain.students.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClass {
    private UUID id;

    private String schoolName;
    private String gradeNum;
    private int classNum;
}
