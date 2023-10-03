package com.ssafy.memberserver.domain.sign.dto;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String memberId;
    private String memberPwd;
}
