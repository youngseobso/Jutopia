package com.ssafy.teacher.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TeacherRequest {
    private String memberId;
    private String memberPwd;
}
