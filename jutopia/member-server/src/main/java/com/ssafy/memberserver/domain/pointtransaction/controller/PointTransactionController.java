package com.ssafy.memberserver.domain.pointtransaction.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointExpenseRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointIncomeRequest;
import com.ssafy.memberserver.domain.pointtransaction.service.PointTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member-server/api/pointtransaction")
    public class PointTransactionController {
        private final PointTransactionService pointTransactionService;
        @Operation(summary = "학생의 포인트 조회")
        @GetMapping("/point/student")
        public ApiResponse getStudentPoint(@RequestParam String studentId) {
            return ApiResponse.success(pointTransactionService.getStudentPoint(studentId));
        }
        @Operation(summary = "포인트 수입")
        @PostMapping("/income")
        public ApiResponse income(@RequestBody PointIncomeRequest pointIncomeRequest){
            return ApiResponse.success(pointTransactionService.pointIncome(pointIncomeRequest));
        }
        @Operation(summary = "포인트 지출")
        @PostMapping("/expense")
        public ApiResponse expense(@RequestBody PointExpenseRequest pointExpenseRequest){
            return ApiResponse.success(pointTransactionService.pointExpense(pointExpenseRequest));
        }
    }