package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.AccountType;
import com.ssafy.memberserver.domain.students.entity.Student;

import java.util.UUID;

public record CreateAccountRequest(
        String accountName,
        String accountNumber,
        AccountType accountType,
        AccountStatus accountStatus,
        UUID studentId
) {
}
