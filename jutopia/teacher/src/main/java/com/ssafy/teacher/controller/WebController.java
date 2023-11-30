package com.ssafy.teacher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.teacher.dto.member.Member;
import com.ssafy.teacher.dto.member.NoticeRequest;
import com.ssafy.teacher.dto.member.PointRequest;
import com.ssafy.teacher.dto.member.TeacherRequest;
import com.ssafy.teacher.dto.rent.SeatRequest;
import com.ssafy.teacher.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/teacher")
public class WebController {

    private final TeacherService service;


    @GetMapping("/login")
    public String login() throws JsonProcessingException {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(String memberId, String memberPwd, HttpSession session) {
        TeacherRequest request = TeacherRequest.builder()
                .memberId(memberId)
                .memberPwd(memberPwd)
                .build();
        Member user = service.login(request);
        log.info("{}", user.toString());
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:http://j9c108.p.ssafy.io:8000/teacher/dashboard";
        } else {
            return "login";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @PostMapping("/createSeat")
    public String createSeat(int totalCount
            , int grade
            , int clazzNumber
            , HttpSession session) {
        Member user = (Member) session.getAttribute("user");
        SeatRequest request = SeatRequest.builder()
                .school(user.getSchool())
//                .school("싸피초등학교")
                .grade(grade)
                .clazzNumber(clazzNumber)
                .totalCount(totalCount)
                .build();
        service.createSeat(request);

        return "redirect:http://j9c108.p.ssafy.io:8000/teacher/dashboard";

    }

    @PostMapping("/createNotice")
    public String createNotice(String title, String content, HttpSession session) {
        Member user = (Member) session.getAttribute("user");
        NoticeRequest request = NoticeRequest.builder()
                .title(title)
                .content(content)
                .school(user.getSchool())
                .grade(user.getGrade())
                .classroom(user.getClassroom())
                .build();

        service.createNotice(request);
        return "redirect:http://j9c108.p.ssafy.io:8000/teacher/dashboard";


    }

    @PostMapping("/providePoints")
    public String providePoints(int points, HttpSession session) {
        Member user = (Member) session.getAttribute("user");
        PointRequest request = PointRequest.builder()
                .school(user.getSchool())
                .grade(user.getGrade())
                .classroom(user.getClassroom())
                .income(new BigDecimal(points))
                .build();

        service.givePoint(request);
        return "redirect:http://j9c108.p.ssafy.io:8000/teacher/dashboard";


    }
}
