package com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TeacherSignUpRequest{
    private String teacherId;
    private String teacherPwd;
    private String teacherName;
    private String teacherEmail;
    private String school;
    private int grade;
    private int classroom;
}
