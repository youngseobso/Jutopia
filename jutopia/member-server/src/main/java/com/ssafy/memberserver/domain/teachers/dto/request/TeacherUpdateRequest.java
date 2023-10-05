package com.ssafy.memberserver.domain.teachers.dto.request;

import lombok.Getter;

@Getter
public class TeacherUpdateRequest {
    private String teacherId;
    private String teacherPwd;
    private String teacherNewPwd;
    private String teacherName;
    private String teacherEmail;
}

