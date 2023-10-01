package com.ssafy.memberserver.domain.students.dto.request;

import lombok.Getter;

@Getter
public class StudentUpdateRequest {
    private String studentId;
    private String studentPwd;
    private String studentNewPwd;
}