package com.ssafy.teacher.client;


import com.ssafy.teacher.dto.common.Response;
import com.ssafy.teacher.dto.rent.Seat;
import com.ssafy.teacher.dto.rent.SeatRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "teacher-rent", url = "http://j9c108.p.ssafy.io:8000/rent-server/api")
@Component
public interface RentClient {

    @GetMapping("/seats")
    Response<List<Seat>> getAllSeat(@RequestParam String school,
                                    @RequestParam int grade,
                                    @RequestParam int clazzNumber);
    @PostMapping("/seats")
    String createSeats(@RequestBody SeatRequest request);

}
