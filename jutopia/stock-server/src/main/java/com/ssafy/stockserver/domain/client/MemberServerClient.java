package com.ssafy.stockserver.domain.client;

import com.ssafy.stockserver.common.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// 호출할 서비스명 선언
@FeignClient(name="member-server")
public interface MemberServerClient {

    @GetMapping("/member-server/api/student/feign/{userId}")
    ApiResponse getStudent(@PathVariable("userId") UUID userId);

    @PutMapping("/member-server/api/student/feign/point")
    ApiResponse memberPointUpdate(@RequestBody MemberPointUpdateRequest memberPointUpdateRequest);
}
