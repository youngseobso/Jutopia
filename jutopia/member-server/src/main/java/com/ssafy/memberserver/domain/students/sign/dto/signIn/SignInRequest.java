package com.ssafy.memberserver.domain.students.sign.dto.signIn;

import com.ssafy.memberserver.common.enums.MemberRole;
import lombok.Getter;

@Getter
public class SignInRequest {
    private String MemberId;
    private String MemberPwd;
    private MemberRole memberRole;
}
