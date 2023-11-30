package com.ssafy.memberserver.domain.students.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="class-server")
public interface ClassServerClient {

    @GetMapping("/class-server/api/school/{schoolName}/{grade}/{classNum}")
    UUID getClassroomId(@PathVariable("schoolName") String schoolName,
                        @PathVariable("grade") int grade,
                        @PathVariable("classNum") int classNum);
}
