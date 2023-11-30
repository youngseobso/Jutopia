package com.ssafy.memberserver.domain.account.dto.response;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.students.entity.Student;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record AccountInfoResponse(
        UUID id,
        String accountName,
        String accountNumber,
        BigDecimal accountBalance,
        AccountStatus accountStatus,
        Student student
) {
    public static AccountInfoResponse from(Account account){
        return AccountInfoResponse.builder()
                .id(account.getId())
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .accountBalance(account.getAccountBalance())
                .accountStatus(account.getAccountStatus())
                .build();
    }
}
