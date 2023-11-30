package com.ssafy.teacher.client;

import com.ssafy.teacher.dto.common.Response;
import com.ssafy.teacher.dto.member.Member;
import com.ssafy.teacher.dto.member.NoticeRequest;
import com.ssafy.teacher.dto.member.PointRequest;
import com.ssafy.teacher.dto.member.TeacherRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "teacher-member", url = "http://j9c108.p.ssafy.io:8000/member-server")

public interface MemberClient {

    @PostMapping("/api/sign-in")
    Response<Member> login(@RequestBody TeacherRequest request);

    @PostMapping("/api/notice/write")
    void createNotice(@RequestBody NoticeRequest request);

    @PutMapping("/api/teacher/helpMoney")
    void givePoint(@RequestParam String school, @RequestParam int grade, @RequestParam int classroom, @RequestParam BigDecimal income);



}
