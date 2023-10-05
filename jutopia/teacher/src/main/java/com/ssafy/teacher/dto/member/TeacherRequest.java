package com.ssafy.teacher.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeacherRequest {
    private String memberId;
    private String memberPwd;
}
