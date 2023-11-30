package com.ssafy.teacher.service;

import com.ssafy.teacher.client.MemberClient;
import com.ssafy.teacher.client.RentClient;
import com.ssafy.teacher.dto.member.Member;
import com.ssafy.teacher.dto.member.NoticeRequest;
import com.ssafy.teacher.dto.member.PointRequest;
import com.ssafy.teacher.dto.member.TeacherRequest;
import com.ssafy.teacher.dto.rent.SeatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final MemberClient memberClient;
    private final RentClient rentClient;

    public Member login(TeacherRequest request){
        return memberClient.login(request).getBody();
    }

    public String createSeat(SeatRequest request){
        return rentClient.createSeats(request);
    }

    public void createNotice(NoticeRequest request){
        memberClient.createNotice(request);
    }

    public void givePoint(PointRequest request){
        memberClient.givePoint(request.getSchool(), request.getGrade(), request.getClassroom(), request.getIncome());
    }
}
