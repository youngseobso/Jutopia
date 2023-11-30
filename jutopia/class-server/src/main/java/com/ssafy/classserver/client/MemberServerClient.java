package com.ssafy.classserver.client;

import com.ssafy.classserver.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

// 호출할 서비스명 선언
@FeignClient(name="member-server")
public interface MemberServerClient {

    @GetMapping("/member-server/api/student/feign/{userId}")
    ApiResponse getStudent(@PathVariable("userId") UUID userId);

    @PutMapping("/member-server/api/student/feign/money")
    ApiResponse memberMoneyUpdate(@RequestBody MemberMoneyUpdateRequest memberMoneyUpdateRequest);
}
