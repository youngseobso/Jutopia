package com.ssafy.rentserver.feignclient;

import com.ssafy.common.api.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "rent-server", url = "http://localhost:8001")
public interface UserServerClient {

    @PutMapping("/api...")
    Api<?> reducePoint(); //userId와 point를 보내서 차감 결과를 리턴받는다.

}
