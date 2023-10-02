package com.ssafy.memberserver.domain.students.sign.dto.signIn;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String memberId;
    private String memberPwd;
}
