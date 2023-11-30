package com.ssafy.memberserver.domain.students.sign.dto.signUp;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.common.enums.SeatOwnershipStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class StudentSignUpRequest {
    private String studentId;
    private String studentPwd;
    private String studentName;
    private String school;
    private int grade;
    private int classRoom;
    private int studentNumber;
    private UUID classroomId;
}
