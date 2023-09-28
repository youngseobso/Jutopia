package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;

public record AccountDeleteRequest(
        AccountStatus accountStatus
) {
}
