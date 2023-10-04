package com.ssafy.teacher.client;

import com.ssafy.teacher.dto.member.Member;
import com.ssafy.teacher.dto.member.TeacherRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "teacher-member", url = "http://j9c108.p.ssafy.io:8000/member-server")

public interface MemberClient {

    @PostMapping("")
    Member login(@RequestBody TeacherRequest request);

}
