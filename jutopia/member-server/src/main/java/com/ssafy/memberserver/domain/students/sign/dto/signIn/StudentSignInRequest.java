package com.ssafy.memberserver.domain.students.sign.dto.signIn;

import lombok.Getter;

@Getter
public class StudentSignInRequest {
    private String studentId;
    private String studentPwd;
}
