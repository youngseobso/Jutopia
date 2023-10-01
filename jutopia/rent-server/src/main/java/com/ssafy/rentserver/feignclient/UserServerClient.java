package com.ssafy.rentserver.feignclient;

import com.ssafy.common.api.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "rent-server", url = "http://j9c108.p.ssafy.io:8000/member-server/api/student")
public interface UserServerClient {

    @PutMapping("/point")
    Api<?> reducePointAndSetSeat(); //userId와 point를 보내서 차감 결과를 리턴받는다.

}
