package com.ssafy.memberserver.domain.sign.dto;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.common.enums.SeatOwnershipStatus;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class SignInResponse {

    private UUID id;
    private String memberId;
    private String name;
    private MemberBioStatus memberBioStatus;
    private BigDecimal money;
    private BigDecimal point;
    private MemberRole memberRole;
    private MemberStatus memberStatus;
    private String school;
    private int grade;
    private int classroom;
    private int studentNumber;
    private SeatOwnershipStatus seatOwnershipStatus;
    private String token;

    public static SignInResponse studentFrom(Student student, String token){
        return SignInResponse.builder()
                .id(student.getId())
                .memberId(student.getStudentId())
                .name(student.getStudentName())
                .memberStatus(student.getMemberStatus())
                .memberRole(student.getMemberRole())
                .memberBioStatus(student.getMemberBioStatus())
                .money(student.getMoney())
                .point(student.getPoint())
                .school(student.getSchool())
                .grade(student.getGrade())
                .classroom(student.getClassRoom())
                .studentNumber(student.getStudentNumber())
                .seatOwnershipStatus(student.getSeatOwnershipStatus())
                .token(token)
                .build();
    }
    public static SignInResponse teacherFrom(Teacher teacher, String token){
        return SignInResponse.builder()
                .id(teacher.getId())
                .memberId(teacher.getTeacherId())
                .name(teacher.getTeacherName())
                .memberStatus(teacher.getMemberStatus())
                .memberRole(teacher.getMemberRole())
                .school(teacher.getSchool())
                .grade(teacher.getGrade())
                .classroom(teacher.getClassroom())
                .token(token)
                .build();
    }
}
