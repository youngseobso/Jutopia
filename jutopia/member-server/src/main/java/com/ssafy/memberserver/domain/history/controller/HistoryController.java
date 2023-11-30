package com.ssafy.memberserver.domain.history.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-server/api/history")
public class HistoryController {
    private final HistoryService historyService;
    @Operation(summary = "특정 계좌의 전체 거래 내역 조회")
    @GetMapping
    public ApiResponse getAccountHistory(@RequestParam String accountId){
        return ApiResponse.success(historyService.getAccountHistory(UUID.fromString(accountId)));
    }
    @Operation(summary = "특정 계좌의 수입 내역 조회")
    @GetMapping("/income")
    public ApiResponse getIncome(@RequestParam String accountId){
        return ApiResponse.success(historyService.getIncome(UUID.fromString(accountId)));
    }
    @Operation(summary = "특정 계좌의 지출 내역 조회")
    @GetMapping("/expense")
    public ApiResponse getExpense(@RequestParam String accountId){
        return ApiResponse.success(historyService.getExpense(UUID.fromString(accountId)));
    }
    @Operation(summary = "특정 계좌의 한달 총 수입액 조회")
    @GetMapping("/incomemonthly")
    public ApiResponse getIncomeMonthly(@RequestParam String accountId,@RequestParam int year,@RequestParam int month) {
        return ApiResponse.success(historyService.getIncomeMonthly(UUID.fromString(accountId),year, month));
    }
    @Operation(summary = "특정 계좌의 한달 총 지출액 조회 ")
    @GetMapping("/expensemonthly")
    public ApiResponse getExpenseMonthly(@RequestParam String accountId,@RequestParam int year,@RequestParam int month){
        return ApiResponse.success(historyService.getExpenseMonthly(UUID.fromString(accountId),year,month));
    }
}
