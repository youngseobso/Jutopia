package com.ssafy.rentserver.controller;

import com.ssafy.common.api.Api;
import com.ssafy.rentserver.dto.CreateRequest;
import com.ssafy.rentserver.dto.SeatChangeRequest;
import com.ssafy.rentserver.dto.SeatRequest;
import com.ssafy.rentserver.dto.SeatResponse;
import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatCacheRepository;
import com.ssafy.rentserver.repository.SeatRepository;
import com.ssafy.rentserver.service.Producer;
import com.ssafy.rentserver.service.SeatService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("rent-server/api")

@Slf4j
public class SeatController {

    private final SeatService seatService;


    @GetMapping("/health_check")
    public String status() {
        return "It's working in Order Service on PORT";
    }

    @PostMapping("/seats")
    public Api<List<Seat>> creatSeats(@RequestBody CreateRequest request){
        log.info(request.toString());
        List<Seat> seats = seatService.createGrid(request.getTotalCount(), request.getClazzNumber(), request.getGrade(), request.getSchool());
        return Api.OK(seats);
    }

    @PutMapping("/seats")
    public Api<?> changeSeatInfo(@RequestBody SeatChangeRequest request){
        return seatService.changeSeatInfo(request);
    }

    @GetMapping("/seats")
    public Api<?> getAllSeats(@RequestParam String school,
                                              @RequestParam int grade,
                                              @RequestParam int clazzNumber)
    {
        return seatService.getAllSeat(clazzNumber, grade, school);
    }

    @GetMapping("/seats/{seatId}")
    public Api<?> getSeatInfo(@PathVariable String seatId){
        return seatService.getSeatInfo(UUID.fromString(seatId));
    }

    @PutMapping("/seats/{seatId}")
    public Api<?> requestSeat(@RequestBody SeatRequest request, @PathVariable String seatId){
        return seatService.requestSeat(request.getSeatId(), request.getUserId());
    }
}
